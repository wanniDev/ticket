package io.ticketaka.api.common.infrastructure.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID
import kotlin.collections.HashMap

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
) {
    fun generate(tsid: String): JwtTokens {
        return JwtTokens(generateAccessToken(tsid), generateRefreshToken(tsid))
    }

    private fun generateAccessToken(tsid: String): String {
        val claims = HashMap<String, Any>()
        claims["tsid"] = tsid
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(jwtProperties.base64TokenSigningKey.toByteArray(StandardCharsets.UTF_8))

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
            .signWith(key)
            .compact()
    }

    private fun generateRefreshToken(tsid: String): String {
        val claims = HashMap<String, Any>()
        claims["tsid"] = tsid
        claims["scopes"] = listOf("REFRESH_TOKEN")
        val randomJti = UUID.randomUUID().toString()
        val currentTime = LocalDateTime.now()
        val key = Keys.hmacShaKeyFor(jwtProperties.base64TokenSigningKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .claims(claims)
            .issuer(jwtProperties.tokenIssuer)
            .issuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .expiration(
                Date.from(
                    currentTime.plusSeconds(jwtProperties.refreshExpirationSec.toLong())
                        .atZone(ZoneId.systemDefault()).toInstant(),
                ),
            )
            .id(randomJti)
            .signWith(key)
            .compact()
    }
}
