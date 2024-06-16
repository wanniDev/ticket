package io.ticketaka.api.user.infrastructure.oauth2

import io.ticketaka.api.user.domain.Attributes
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class NaverUser(
    private val attributes: Attributes,
    private val oAuth2User: OAuth2User,
    private val clientRegistration: ClientRegistration,
) : Oauth2ProviderUser(attributes.subAttributes, oAuth2User, clientRegistration) {
    override fun getId(): String {
        return getAttributes()["id"] as String
    }

    override fun getUsername(): String {
        return getAttributes()["name"] as String
    }
}
