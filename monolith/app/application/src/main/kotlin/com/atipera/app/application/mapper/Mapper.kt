package com.atipera.app.application.mapper

import com.atipera.app.ports.input.BranchProjection
import com.atipera.app.ports.input.RepositoryProjection
import com.atipera.app.ports.output.BranchDto
import com.atipera.app.ports.output.RepositoryWithBranchesDto

fun RepositoryWithBranchesDto.toRepositoryProjection(): RepositoryProjection =
    RepositoryProjection(
        repositoryName = this.name,
        ownerLogin = this.owner,
        branches = this.branches.map { it.toProjection() }
    )


fun BranchDto.toProjection() = BranchProjection(branchName = this.branchName, lastCommitSha = this.sha)


fun List<RepositoryWithBranchesDto>.toRepositoryProjectionList(): List<RepositoryProjection> =
    this.map { it.toRepositoryProjection() }
