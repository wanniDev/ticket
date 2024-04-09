package io.ticketaka.api.common.infrastructure.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
@Order(1)
class JwtFilter(
    private val jwtTokenParser: JwtTokenParser,
): OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val AUTHORIZATION_HEADER = "Authorization"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("JwtFilter")
        val header = request.getHeader(AUTHORIZATION_HEADER)
        val tokenMap = jwtTokenParser.parse(header)

        // TODO 사용자 검증
        filterChain.doFilter(request, response)
    }
}