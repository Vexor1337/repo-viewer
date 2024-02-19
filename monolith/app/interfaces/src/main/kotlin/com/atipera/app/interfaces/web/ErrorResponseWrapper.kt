package com.atipera.app.interfaces.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ErrorResponseWrapper(
    val status: Int,
    val message: String?
)

fun String.toErrorResponse(status: HttpStatus): ResponseEntity<ErrorResponseWrapper> =
    ResponseEntity(
        ErrorResponseWrapper(
            status = status.value(),
            message = this,
        ),
        status
    )