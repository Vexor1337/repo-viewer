package com.atipera.app.application

import com.atipera.app.ports.output.GitHubClient
import com.atipera.app.commons.GitHubClientException
import com.atipera.app.ports.input.RepositoryProjection
import arrow.core.Either
import com.atipera.app.application.mapper.toRepositoryProjectionList
import org.springframework.stereotype.Service
import com.atipera.app.ports.input.service.RepoViewerService
@Service
class RepoViewerServiceImpl(private val gitHubClient: GitHubClient): RepoViewerService {
    override fun getRepositoriesListWithBranchesByUsername(username: String): Either<GitHubClientException, List<RepositoryProjection>> =
        gitHubClient.getGitHubRepositoriesWithBranchesByUsername(username).map { it.toRepositoryProjectionList() }
}