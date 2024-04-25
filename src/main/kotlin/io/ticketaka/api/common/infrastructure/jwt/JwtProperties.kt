package io.ticketaka.api.common.infrastructure.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProperties(
    @Value("\${jwt.tokenExpirationSec}")
    val tokenExpirationSec: Long,
    @Value("\${jwt.refreshExpirationSec}")
    val refreshExpirationSec: Long,
    @Value("\${jwt.tokenIssuer}")
    val tokenIssuer: String,
    @Value("\${jwt.base64TokenSigningKey}")
    val base64TokenSigningKey: String,
)
