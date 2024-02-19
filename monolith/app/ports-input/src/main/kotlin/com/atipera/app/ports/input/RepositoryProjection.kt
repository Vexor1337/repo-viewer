package com.atipera.app.ports.input

data class RepositoryProjection(
    val repositoryName: String,
    val ownerLogin: String,
    val branches : List<BranchDto>
)
data class BranchDto(
    val branchName: String,
    val lastCommitSha: String
)


