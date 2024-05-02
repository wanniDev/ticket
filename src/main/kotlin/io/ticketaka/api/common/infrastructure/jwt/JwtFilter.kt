package io.ticketaka.api.common.infrastructure.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

// @Component
// @Order(1)
class JwtFilter(
    private val jwtTokenParser: JwtTokenParser,
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)

//    private val authorizationHeader = "Authorization"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        log.info("JwtFilter")
        if (request.requestURI.contains("/api/token") ||
            request.requestURI.contains("/h2-console") ||
            request.requestURI.contains("/swagger") ||
            request.requestURI.contains("/api-docs")
        ) {
            filterChain.doFilter(request, response)
            return
        }

//        val header = request.getHeader(authorizationHeader)
//        val tokenMap = jwtTokenParser.parse(header)

        // TODO 사용자 검증
        filterChain.doFilter(request, response)
    }
}
