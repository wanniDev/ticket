package io.ticketaka.api.user.domain

import org.springframework.security.core.GrantedAuthority

interface ProviderUser {
    fun getId(): String

    fun getUsername(): String

    fun getPassword(): String

    fun getEmail(): String

    fun getProvider(): String

    fun getRoles(): List<GrantedAuthority>

    fun getAttributes(): Map<String, Any>
}
