package com.atipera.app.interfaces

import com.atipera.app.commons.GitHubApiUnauthorizedException
import com.atipera.app.commons.GitHubApiUnexpectedException
import com.atipera.app.commons.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus

class RepoViewerControllerExceptionHandlerTest {

    @Mock
    private lateinit var exception: RepoViewerEitherException
    private val handler = RepoViewerExceptionHandler()

    init {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test handleRuntimeException RepoViewerGenericException`() {
        val errorMessage = "Generic exception"
        `when`(exception.eitherError).thenReturn(GitHubApiUnexpectedException(errorMessage))

        val response = handler.handleRuntimeException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(errorMessage, response.body?.message)
    }

    @Test
    fun `test handle UserNotFoundException`() {
        val user = "User"
        val expectedResponse = UserNotFoundException(user).message
        `when`(exception.eitherError).thenReturn(UserNotFoundException(user))

        val response = handler.handleRuntimeException(exception)

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(expectedResponse, response.body?.message)
    }

    @Test
    fun `test handle GitHubApiUnauthorizedException`() {
        val expectedResponse = GitHubApiUnauthorizedException().message
        `when`(exception.eitherError).thenReturn(GitHubApiUnauthorizedException())

        val response = handler.handleRuntimeException(exception)

        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        assertEquals(expectedResponse, response.body?.message)
    }

}