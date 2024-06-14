package io.ticketaka.api.user.infrastructure.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.ticketaka.api.common.ApiError
import io.ticketaka.api.user.domain.exception.AlreadyTokenExpiredException
import io.ticketaka.api.user.domain.exception.InvalidTokenException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: InvalidTokenException) {
            streamErrorResponse(response, exception)
        } catch (exception: AlreadyTokenExpiredException) {
            streamErrorResponse(response, exception)
        }
    }

    private fun extractErrorCode(exception: Exception): Int {
        return when (exception) {
            is InvalidTokenException -> 400
            is AlreadyTokenExpiredException -> 401
            else -> 500
        }
    }

    private fun streamErrorResponse(
        response: HttpServletResponse,
        exception: Exception,
    ) {
        val errorCode = extractErrorCode(exception)
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = "application/json; charset=UTF-8"
        response.writer.write(
            objectMapper
                .writeValueAsString(ApiError(errorCode, exception.message ?: "내부 서버 에러 발생")),
        )
    }
}
