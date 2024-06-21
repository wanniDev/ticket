package io.ticketaka.api.user.infrastructure.oauth2.converter

import io.ticketaka.api.user.domain.ProviderUser
import io.ticketaka.api.user.infrastructure.oauth2.KakaoUser
import io.ticketaka.api.user.infrastructure.oauth2.ProviderUserRequest

class OAuth2KakaoProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun convert(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if (providerUserRequest.clientRegistration.registrationId != "kakao") {
            return null
        }

        return KakaoUser(
            ProviderUserAttributeGenerator
                .generate(providerUserRequest.oAuth2User, "kakao_account", "profile"),
            providerUserRequest.oAuth2User,
            providerUserRequest.clientRegistration,
        )
    }
}
