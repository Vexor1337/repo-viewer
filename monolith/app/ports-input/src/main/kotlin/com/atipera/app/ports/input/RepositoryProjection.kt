package com.atipera.app.ports.input

data class RepositoryProjection(
    val repositoryName: String,
    val ownerLogin: String,
    val branches : List<BranchProjection>
)
data class BranchProjection(
    val branchName: String,
    val lastCommitSha: String
)
val branch1 = BranchProjection("ss", "dd")
val branch2 = BranchProjection("ss", "dd")
val xd = RepositoryProjection("Repo 1", "Owner 1", branches = listOf(BranchProjection("d",""), branch2))

