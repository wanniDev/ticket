package io.ticketaka.api.user.infrastructure.oauth2

import io.ticketaka.api.user.domain.ProviderUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.UUID

abstract class Oauth2ProviderUser(
    private val attributes: Map<String, Any>,
    private val oAuth2User: OAuth2User,
    private val clientRegistration: ClientRegistration,
) : ProviderUser {
    private var isCertified: Boolean = false

    override fun getPassword(): String {
        return UUID.randomUUID().toString()
    }

    override fun getEmail(): String {
        return getAttributes()["email"] as String
    }

    override fun getProvider(): String {
        return clientRegistration.registrationId
    }

    override fun getRoles(): List<GrantedAuthority> {
        return oAuth2User
            .authorities
            .map { SimpleGrantedAuthority(it.authority) }
            .toList()
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }
}
