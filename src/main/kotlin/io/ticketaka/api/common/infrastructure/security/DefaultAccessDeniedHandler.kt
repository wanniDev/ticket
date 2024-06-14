package io.ticketaka.api.common.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.ticketaka.api.common.ApiError
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class DefaultAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?,
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        val outputStream = response.outputStream
        objectMapper
            .writeValue(outputStream, ApiError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다."))
    }
}
