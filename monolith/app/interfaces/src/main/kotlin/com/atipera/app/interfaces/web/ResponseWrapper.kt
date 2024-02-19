package com.atipera.app.interfaces.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

typealias Response<T> = ResponseEntity<ResponseWrapper<T>>

class ResponseWrapper<T>(val data: T)

fun <T> T.toResponse(status: HttpStatus = HttpStatus.OK): Response<T> =
    ResponseEntity(ResponseWrapper(this), status)
