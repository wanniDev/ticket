package io.ticketaka.api.user.presentation

import io.ticketaka.api.user.infrastructure.oauth2.PrincipalUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping("/")
    fun index(
        model: Model,
        @AuthenticationPrincipal principalUser: PrincipalUser?,
    ): String {
        val view = "index"

        if (principalUser != null) {
            val email = principalUser.providerUser.getEmail()
            model.addAttribute("email", email)
            model.addAttribute("provider", principalUser.providerUser.getProvider())
        }

        return view
    }
}
