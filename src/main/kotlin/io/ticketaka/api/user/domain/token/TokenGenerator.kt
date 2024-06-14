package io.ticketaka.api.user.domain.token

import io.ticketaka.api.user.domain.Role

interface TokenGenerator {
    fun generate(
        subject: String,
        roles: Set<Role>,
    ): QueueToken
}
