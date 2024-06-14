package io.ticketaka.api.common.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.ticketaka.api.common.ApiError
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class DefaultAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?,
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.setHeader("content-type", "application/json;charset=utf8")
        val outputStream = response.outputStream
        objectMapper
            .writeValue(outputStream, ApiError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다."))
    }
}
