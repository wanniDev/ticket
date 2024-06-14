package io.ticketaka.api.user.infrastructure.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import io.ticketaka.api.user.domain.Role
import io.ticketaka.api.user.domain.token.TokenGenerator
import io.ticketaka.api.user.domain.token.Tokens
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID

@Component
class JwtTokenGenerator(
    private val jwtProperties: JwtProperties,
) : TokenGenerator {
    private val encodedSigningKey = Encoders.BASE64.encode(jwtProperties.base64TokenSigningKey.toByteArray())

    override fun generate(
        subject: String,
        roles: Set<Role>,
    ): Tokens {
        return Tokens(generateAccessToken(subject, roles), generateRefreshToken(subject))
    }

    private fun generateAccessToken(
        subject: String,
        roles: Set<Role>,
    ): String {
        val claims = HashMap<String, Any>()
        claims["subject"] = subject
        claims["scopes"] = getAuthorities(roles)
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSigningKey))

        return Jwts.builder()
            .claims(claims)
            .issuer(jwtProperties.tokenIssuer)
            .issuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .expiration(
                Date.from(
                    currentTime.plusSeconds(jwtProperties.tokenExpirationSec)
                        .atZone(ZoneId.systemDefault()).toInstant(),
                ),
            )
            .signWith(key)
            .compact()
    }

    private fun generateRefreshToken(subject: String): String {
        val claims = HashMap<String, Any>()
        claims["subject"] = subject
        claims["scopes"] = listOf(Scopes.REFRESH_TOKEN.authority())
        val randomJti = UUID.randomUUID().toString()
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSigningKey))

        return Jwts.builder()
            .claims(claims)
            .issuer(jwtProperties.tokenIssuer)
            .issuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .expiration(
                Date.from(
                    currentTime.plusSeconds(jwtProperties.refreshExpirationSec)
                        .atZone(ZoneId.systemDefault()).toInstant(),
                ),
            )
            .id(randomJti)
            .signWith(key)
            .compact()
    }

    private fun getAuthorities(roles: Set<Role>): List<String> {
        return roles.map { "ROLE_${it.name}" }
    }
}
