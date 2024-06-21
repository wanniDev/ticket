package io.ticketaka.api.user.infrastructure.oauth2

import io.ticketaka.api.user.domain.ProviderUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

data class PrincipalUser(
    val providerUser: ProviderUser,
) : OAuth2User {
    override fun getName(): String {
        return providerUser.getUsername()
    }

    override fun getAttributes(): Map<String, Any> {
        return providerUser.getAttributes()
    }

    override fun <A : Any?> getAttribute(name: String?): A? {
        return super.getAttribute(name)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return providerUser.getRoles().toMutableList()
    }
}
