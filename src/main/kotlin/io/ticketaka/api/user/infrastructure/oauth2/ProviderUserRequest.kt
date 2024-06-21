package io.ticketaka.api.user.infrastructure.oauth2

import io.ticketaka.api.user.domain.User
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

data class ProviderUserRequest(
    val clientRegistration: ClientRegistration,
    val oAuth2User: OAuth2User,
    val user: User? = null,
)
