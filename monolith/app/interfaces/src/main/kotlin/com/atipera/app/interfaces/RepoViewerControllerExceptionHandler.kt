package com.atipera.app.interfaces
import com.atipera.app.commons.GitHubApiUnauthorizedException
import com.atipera.app.commons.GitHubApiUnexpectedException
import com.atipera.app.commons.GitHubClientException
import com.atipera.app.commons.UserNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.atipera.app.interfaces.web.ErrorResponseWrapper
import com.atipera.app.interfaces.web.toErrorResponse

class RepoViewerEitherException(
    val eitherError: GitHubClientException, val status: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException()

fun GitHubClientException.toException(status: HttpStatus = HttpStatus.BAD_REQUEST) =
    RepoViewerEitherException(eitherError = this, status = status)

@RestControllerAdvice(
    basePackages = ["com.atipera.app.interfaces"]
)
@Order(100)
class LolRanksControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(LolRanksControllerExceptionHandler::class.java)

    @ExceptionHandler(RepoViewerEitherException::class)
    fun handleRuntimeException(exception: RepoViewerEitherException): ResponseEntity<ErrorResponseWrapper> =
        when (val e = exception.eitherError) {
            is GitHubApiUnauthorizedException -> e.message.toErrorResponse(HttpStatus.UNAUTHORIZED)
            is GitHubApiUnexpectedException -> e.message.toErrorResponse(HttpStatus.BAD_REQUEST)
            is UserNotFoundException -> e.message.toErrorResponse(HttpStatus.NOT_FOUND)
        }

    @ExceptionHandler(GitHubApiUnexpectedException::class)
    fun handleGitHubApiUnexpectedException(exception: GitHubApiUnexpectedException): ResponseEntity<ErrorResponseWrapper> =
        exception.log().message.toErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY)
    private fun <T : RuntimeException> T.log(): T =
        apply { logger.warn(this.message + " " + this.stackTraceToString()) }
}