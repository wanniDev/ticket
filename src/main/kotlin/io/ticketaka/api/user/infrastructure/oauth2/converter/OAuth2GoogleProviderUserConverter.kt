package io.ticketaka.api.user.infrastructure.oauth2.converter

import io.ticketaka.api.user.domain.ProviderUser
import io.ticketaka.api.user.infrastructure.oauth2.GoogleUser
import io.ticketaka.api.user.infrastructure.oauth2.ProviderUserRequest

class OAuth2GoogleProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (providerUserRequest.clientRegistration.registrationId != "google") {
            return null
        }

        return GoogleUser(
            ProviderUserAttributeGenerator.generate(providerUserRequest.oAuth2User),
            providerUserRequest.oAuth2User,
            providerUserRequest.clientRegistration,
        )
    }
}
