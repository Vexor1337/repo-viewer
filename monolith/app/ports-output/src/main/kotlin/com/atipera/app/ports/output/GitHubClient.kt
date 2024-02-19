package com.atipera.app.ports.output

import arrow.core.Either
import com.atipera.app.commons.GitHubClientException
import org.springframework.stereotype.Repository

@Repository
interface GitHubClient {
    fun getGitHubRepositoriesWithBranchesByUsername(user: String) : Either<GitHubClientException, List<RepositoryWithBranchesDto>>
}