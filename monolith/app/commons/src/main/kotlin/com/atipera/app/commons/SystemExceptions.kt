package com.atipera.app.commons

sealed interface GitHubClientException : EitherError


class GitHubApiUnauthorizedException() : GitHubClientException {
    override val message: String
        get() = "Wrong GitHub Personal Api token"
}
class UserNotFoundException(private val user: String) : GitHubClientException {
    override val message: String
        get() = "User: $user not found on github"
}
class GitHubApiUnexpectedException(override val message: String) : GitHubClientException, RuntimeException()