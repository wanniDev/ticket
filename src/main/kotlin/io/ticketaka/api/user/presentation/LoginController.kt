package io.ticketaka.api.user.presentation

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/login")
    fun login(): String = "login"

    @GetMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ): String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)

        return "redirect:/login"
    }
}
