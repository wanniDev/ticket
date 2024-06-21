package io.ticketaka.api.user.application

import io.ticketaka.api.user.domain.ProviderUser
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.infrastructure.oauth2.PrincipalUser
import io.ticketaka.api.user.infrastructure.oauth2.ProviderUserRequest
import io.ticketaka.api.user.infrastructure.oauth2.converter.ProviderUserConverter
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val providerUserConverter: ProviderUserConverter<ProviderUserRequest, ProviderUser>,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val clientRegistration = userRequest.clientRegistration
        val oAuth2UserService = DefaultOAuth2UserService()
        val oAuth2User = oAuth2UserService.loadUser(userRequest)

        val providerUserRequest = ProviderUserRequest(clientRegistration, oAuth2User)
        val providerUser =
            providerUserConverter.convert(providerUserRequest)
                ?: throw IllegalArgumentException("Not supported provider")

        registerIfAbsent(providerUser)

        return PrincipalUser(providerUser)
    }

    private fun registerIfAbsent(providerUser: ProviderUser) {
        val user = userRepository.findByEmail(providerUser.getEmail())
        if (user == null) {
            userRepository.save(User.newInstance(providerUser.getEmail()))
        }
    }
}
