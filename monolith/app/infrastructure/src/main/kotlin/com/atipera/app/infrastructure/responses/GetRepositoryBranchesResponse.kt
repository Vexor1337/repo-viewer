package com.atipera.app.infrastructure.responses

data class GetRepositoryBranchesResponse(
    val name: String,
    val commit: Commit
)
data class Commit(
    val sha: String
)