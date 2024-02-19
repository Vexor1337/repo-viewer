package com.atipera.app.ports.output

data class RepositoryWithBranchesDto(
    val name: String,
    val owner: String,
    val branches : List<BranchDto>
)
data class BranchDto(
    val branchName: String,
    val sha: String
)


