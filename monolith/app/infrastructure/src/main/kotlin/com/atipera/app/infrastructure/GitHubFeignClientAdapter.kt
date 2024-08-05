package com.atipera.app.infrastructure

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.atipera.app.commons.GitHubApiUnauthorizedException
import com.atipera.app.commons.GitHubApiUnexpectedException
import com.atipera.app.commons.GitHubClientException
import com.atipera.app.commons.UserNotFoundException
import com.atipera.app.infrastructure.client.GitHubFeignClient
import com.atipera.app.ports.output.BranchDto
import com.atipera.app.ports.output.GitHubClient
import com.atipera.app.ports.output.RepositoriesDto
import com.atipera.app.ports.output.RepositoryWithBranchesDto
import feign.FeignException
import org.springframework.stereotype.Repository

@Repository
class GitHubFeignClientAdapter(private val gitHubFeignClient: GitHubFeignClient) : GitHubClient {
    override fun getGitHubRepositoriesWithBranchesByUsername(user: String): Either<GitHubClientException, List<RepositoryWithBranchesDto>> =
         getGitHubRepositoriesList(user).fold(
            { e -> e.left() },
            { repositories ->
                val repositoriesWithBranches = repositories.map { repository ->
                    val branches = gitHubFeignClient.getRepositoryBranches(user, repository.name)
                        .map { branch -> BranchDto(branchName = branch.name, sha = branch.commit.sha) }
                        .collectList()
                        .block()
                    RepositoryWithBranchesDto(
                        name = repository.name,
                        owner = repository.ownerLogin,
                        branches = branches ?: emptyList()
                    )
                }
                repositoriesWithBranches.right()
            }
        )


    private fun getGitHubRepositoriesList(user: String): Either<GitHubClientException, List<RepositoriesDto>> =
        Either.catch {
            gitHubFeignClient.getUserRepositories(user)
                .map { RepositoriesDto(name = it.name, ownerLogin = it.owner.login) }
                .collectList()
                .block()!!.toList()
        }.mapLeft { e ->
        when (e) {
            is FeignException.Unauthorized -> GitHubApiUnauthorizedException()
            is FeignException.NotFound -> UserNotFoundException(user = user)
            else -> GitHubApiUnexpectedException(e.message!!)
        }

    }
}


