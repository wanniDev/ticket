package io.ticketaka.api.common

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundUserException(e: NotFoundException): ResponseEntity<ApiError> {
        log.error("User Not Found: {}", e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(404, e.message ?: "404 에러 발생"))
    }

    @ExceptionHandler(BadClientRequestException::class)
    fun handleBadRequestException(e: BadClientRequestException): ResponseEntity<ApiError> {
        log.error("Bad Request: {}", e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(400, e.message ?: "400 에러 발생"))
    }
}
