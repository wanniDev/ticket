package io.ticketaka.api.user.infrastructure.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import io.ticketaka.api.user.domain.exception.AlreadyTokenExpiredException
import io.ticketaka.api.user.domain.exception.InvalidTokenException
import io.ticketaka.api.user.domain.token.RawToken
import io.ticketaka.api.user.domain.token.TokenParser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class JwtTokenParser(
    private val jwtProperties: JwtProperties,
) : TokenParser {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val encodedSigningKey = Encoders.BASE64.encode(jwtProperties.base64TokenSigningKey.toByteArray())

    override fun parse(token: String): RawToken {
        try {
            val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSigningKey))
            val jwt: Jws<Claims> =
                Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
            val payload = jwt.payload
            val jti =
                payload["jti"] as String?
                    ?: return RawToken(
                        subject = payload["subject"] as String,
                        authorities = payload["scopes"] as List<String>,
                    )
            return RawToken(
                subject = payload["subject"] as String,
                jti = jti,
                authorities = payload["scopes"] as List<String>,
            )
        } catch (ex: UnsupportedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (ex: IllegalArgumentException) {
            log.error("Invalid JWT Token", ex)
            throw InvalidTokenException()
        } catch (expiredEx: ExpiredJwtException) {
            log.info("JWT Token is expired", expiredEx)
            throw AlreadyTokenExpiredException()
        }
    }
}
