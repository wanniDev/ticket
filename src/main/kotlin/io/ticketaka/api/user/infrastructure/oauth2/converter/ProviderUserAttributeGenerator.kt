package io.ticketaka.api.user.infrastructure.oauth2.converter

import io.ticketaka.api.user.domain.Attributes
import org.springframework.security.oauth2.core.user.OAuth2User

object ProviderUserAttributeGenerator {
    fun generate(
        oAuth2User: OAuth2User,
        subAttributesKey: String = "",
        otherAttributesKey: String = "",
    ): Attributes =
        Attributes(
            mainAttributes = oAuth2User.attributes,
            subAttributes = oAuth2User.attributes[subAttributesKey] as Map<String, Any>,
            otherAttributes =
                if (otherAttributesKey.isNotBlank()) {
                    oAuth2User.attributes[otherAttributesKey] as Map<String, Any>
                } else {
                    emptyMap()
                },
        )
}
