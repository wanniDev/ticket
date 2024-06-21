package io.ticketaka.api.user.infrastructure.jwt

import io.ticketaka.api.user.domain.AuthenticatedUser
import io.ticketaka.api.user.domain.Role
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.domain.token.TokenExtractor
import io.ticketaka.api.user.domain.token.TokenParser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val userRepository: UserRepository,
    private val tokenExtractor: TokenExtractor,
    private val tokenParser: TokenParser,
) : OncePerRequestFilter() {
    private val authorizationHeader = "Authorization"

    @Throws(RuntimeException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val header = request.getHeader(authorizationHeader)
        val rawToken = tokenExtractor.extract(header)
        if (rawToken.isNotBlank()) {
            val tokenMap = tokenParser.parse(rawToken)
            val userId = tokenMap.subject.toLong()
            val user = userRepository.findById(userId)

            if (user != null) {
                val context =
                    UsernamePasswordAuthenticationToken(
                        AuthenticatedUser(user.getId(), user.roles),
                        null,
                        authorities(user.roles),
                    )
                SecurityContextHolder.getContext().authentication = context
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun authorities(roles: Set<Role>): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }.toList()
    }
}
