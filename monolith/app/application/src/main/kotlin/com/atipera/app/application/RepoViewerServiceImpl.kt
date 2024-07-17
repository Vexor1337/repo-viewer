package com.atipera.app.application

import arrow.core.Either
import com.atipera.app.application.mapper.toRepositoryProjectionList
import com.atipera.app.commons.GitHubClientException
import com.atipera.app.ports.input.RepositoryProjection
import com.atipera.app.ports.input.service.RepoViewerService
import com.atipera.app.ports.output.GitHubClient
import org.springframework.stereotype.Service

@Service
class RepoViewerServiceImpl(private val gitHubClient: GitHubClient): RepoViewerService {

    override fun getRepositoriesListWithBranchesByUsername(username: String): Either<GitHubClientException, List<RepositoryProjection>> =
        gitHubClient.getGitHubRepositoriesWithBranchesByUsername(username).map { it.toRepositoryProjectionList() }
}