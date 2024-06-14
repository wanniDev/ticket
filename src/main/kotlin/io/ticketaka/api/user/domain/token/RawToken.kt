package io.ticketaka.api.user.domain.token

import io.ticketaka.api.user.domain.exception.InvalidTokenException

data class RawToken(
    val subject: String,
    val jti: String? = null,
    val authorities: List<String>,
) {
    fun verify(authority: String) {
        if (!authorities.stream().anyMatch { it.equals(authority) }) {
            throw InvalidTokenException()
        }
    }
}
