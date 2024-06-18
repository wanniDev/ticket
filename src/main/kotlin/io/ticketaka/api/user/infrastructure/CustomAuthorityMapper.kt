package io.ticketaka.api.user.infrastructure

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper

class CustomAuthorityMapper : GrantedAuthoritiesMapper {
    private val prefix = "ROLE_"

    override fun mapAuthorities(authorities: MutableCollection<out GrantedAuthority>?): Set<GrantedAuthority> {
        val authorityList =
            authorities?.map { authority ->
                mapAuthority(authority.authority)
            } ?: emptyList()
        return authorityList.toSet()
    }

    private fun mapAuthority(authority: String): GrantedAuthority {
        var name = authority
        if (name.lastIndexOf(".") > 0) {
            val index: Int = name.lastIndexOf(".")
            name = "SCOPE_" + name.substring(index + 1)
        }
        if (!name.startsWith(prefix)) {
            name = prefix + name
        }
        return SimpleGrantedAuthority(name)
    }
}
