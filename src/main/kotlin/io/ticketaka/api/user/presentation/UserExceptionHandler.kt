package io.ticketaka.api.user.presentation

import io.ticketaka.api.common.ApiError
import io.ticketaka.api.user.infrastructure.persistence.exception.NotFoundUserException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(NotFoundUserException::class)
    fun handleNotFoundUserException(e: NotFoundUserException): ResponseEntity<ApiError> {
        log.error("User Not Found: {}", e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(404, "사용자를 찾을 수 없습니다."))
    }
}