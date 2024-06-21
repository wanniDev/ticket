package io.ticketaka.api.user.domain

data class AuthenticatedUser(
    val userId: Long,
    val roles: Set<Role>,
)
