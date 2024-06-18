package io.ticketaka.api.user.presentation

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/api/user")
    fun user(
        authentication: Authentication,
        @AuthenticationPrincipal oAuth2User: OAuth2User,
    ): Authentication {
        logger.info("authentication: $authentication, oAuth2User: $oAuth2User")
        return authentication
    }
}
