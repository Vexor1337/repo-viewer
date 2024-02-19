package com.atipera.app.ports.input.service

import com.atipera.app.commons.GitHubClientException
import com.atipera.app.ports.input.RepositoryProjection
import arrow.core.Either
import org.springframework.stereotype.Service

@Service
interface RepoViewerService {
    fun getRepositoriesListWithBranchesByUsername(username: String) : Either<GitHubClientException, List<RepositoryProjection>>
}