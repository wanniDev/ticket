package io.ticketaka.api.common.infrastructure.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtTokenParser(
    private val jwtProperties: JwtProperties,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val headerPrefix = "Bearer "
    private val base64TokenSignKey = jwtProperties.base64TokenSigningKey

    fun parse(token: String): RawToken {
        try {
            val bytes = Decoders.BASE64.decode(base64TokenSignKey)
            val key: SecretKey = Keys.hmacShaKeyFor(bytes)

            val jwt =
                Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(extract(token))
            return RawToken(jwt.payload["tsid"] as String)
        } catch (ex: UnsupportedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw IllegalArgumentException(ex.message)
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT Token", ex)
            throw IllegalArgumentException()
        } catch (ex: IllegalArgumentException) {
            log.error("Invalid JWT Token", ex)
            throw IllegalArgumentException(ex.message)
        } catch (ex: ExpiredJwtException) {
            log.info("JWT Token is expired", ex)
            throw IllegalArgumentException(ex.message)
        }
    }

    private fun extract(payload: String?): String {
        return if (payload.isNullOrEmpty()) {
            ""
        } else {
            payload.substring(headerPrefix.length)
        }
    }
}
