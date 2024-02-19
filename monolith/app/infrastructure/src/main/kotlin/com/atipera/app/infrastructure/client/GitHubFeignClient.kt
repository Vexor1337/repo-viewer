package com.atipera.app.infrastructure.client

import feign.Param
import feign.RequestLine
import com.atipera.app.infrastructure.responses.GetRepositoryBranchesResponse
import com.atipera.app.infrastructure.responses.GetUserRepositoriesResponse

interface GitHubFeignClient {
    @RequestLine("GET users/{user}/repos")
    fun getUserRepositories(@Param user: String) : List<GetUserRepositoriesResponse>

    @RequestLine("GET repos/{user}/{repository}/branches")
    fun getRepositoryBranches(@Param user: String, @Param repository: String): List<GetRepositoryBranchesResponse>
}

