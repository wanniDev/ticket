package io.ticketaka.api.user.infrastructure.oauth2.converter

import io.ticketaka.api.user.domain.ProviderUser
import io.ticketaka.api.user.infrastructure.oauth2.NaverUser
import io.ticketaka.api.user.infrastructure.oauth2.ProviderUserRequest

class OAuth2NaverProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (!providerUserRequest.clientRegistration.registrationId.equals("naver")) {
            return null
        }

        return NaverUser(
            ProviderUserAttributeGenerator.generate(providerUserRequest.oAuth2User),
            providerUserRequest.oAuth2User,
            providerUserRequest.clientRegistration,
        )
    }
}
