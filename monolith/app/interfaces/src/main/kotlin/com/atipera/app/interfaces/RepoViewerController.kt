package com.atipera.app.interfaces

import arrow.core.Either
import com.atipera.app.ports.input.RepositoryProjection
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.atipera.app.ports.input.service.RepoViewerService
import com.atipera.app.interfaces.web.Response
import com.atipera.app.interfaces.web.toResponse

@RestController
@RequestMapping("/repo-viewer")
class RepoViewerController(private val repoViewerService: RepoViewerService) {
    @GetMapping("/getRepositoriesList")
    fun getRepositoriesByUsername(@RequestParam username: String): Response<List<RepositoryProjection>> {
        return when (val result = repoViewerService.getRepositoriesListWithBranchesByUsername(username)) {
            is Either.Right -> result.value.toResponse()
            is Either.Left -> throw result.value.toException()

        }
    }

}