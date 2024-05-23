package io.ticketaka.api.user.presentation

import io.ticketaka.api.user.application.TokenUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/token")
class TokenApi(
    private val tokenUserService: TokenUserService,
) : TokenApiSpecification {
    @PostMapping
    override fun createToken(
        @RequestBody request: CreateTokenRequest,
    ): ResponseEntity<CreateTokenResponse> {
        val createdTokenTsid = tokenUserService.createToken(request.userTsid)
        return ResponseEntity
            .ok(CreateTokenResponse(createdTokenTsid))
    }

    @PostMapping("/peek")
    override fun peekToken(): ResponseEntity<PeekTokenResponse> {
        val result = tokenUserService.peekToken()
        return ResponseEntity
            .ok(PeekTokenResponse(result, LocalDateTime.now()))
    }
}
