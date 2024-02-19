package com.atipera.app.application.mapper

import com.atipera.app.ports.input.BranchDto
import com.atipera.app.ports.input.RepositoryProjection
import com.atipera.app.ports.output.RepositoryWithBranchesDto
fun RepositoryWithBranchesDto.toRepositoryProjection(): RepositoryProjection =
    RepositoryProjection(
        repositoryName = this.name,
        ownerLogin = this.owner,
        branches = this.branches.map { it.toInput().toBranchDto() }
    )


fun com.atipera.app.ports.output.BranchDto.toInput() = BranchDto(branchName = this.branchName, lastCommitSha = this.sha)
fun BranchDto.toBranchDto(): BranchDto = this

fun List<RepositoryWithBranchesDto>.toRepositoryProjectionList(): List<RepositoryProjection> =
    this.map { it.toRepositoryProjection() }
//fun List<RepositoryWithBranchesDto>.toProjection(): List<RepositoryProjection> = Mappers.getMapper(mapper.Mapper::class.java).repositoryWithBranchesDtoToRepositoryProjection(this)