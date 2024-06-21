package io.ticketaka.api.user.infrastructure.oauth2.converter

import io.ticketaka.api.user.domain.ProviderUser
import io.ticketaka.api.user.infrastructure.oauth2.ProviderUserRequest
import org.springframework.stereotype.Component

@Component
class DelegatingProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    private val converters = mutableListOf<ProviderUserConverter<ProviderUserRequest, ProviderUser>>()

    init {
        listOf(
            OAuth2GoogleProviderUserConverter(),
            OAuth2NaverProviderUserConverter(),
            OAuth2KakaoProviderUserConverter(),
        ).forEach { converters.add(it) }
    }

    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        converters.forEach { converter ->
            val providerUser = converter.convert(providerUserRequest)
            if (providerUser != null) {
                return providerUser
            }
        }
        return null
    }
}
