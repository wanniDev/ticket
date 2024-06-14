package io.ticketaka.api.user.domain.token

import io.ticketaka.api.user.domain.Role

interface TokenReIssuer {
    fun reIssue(
        refreshToken: String,
        roles: Set<Role>,
    ): Tokens
}
