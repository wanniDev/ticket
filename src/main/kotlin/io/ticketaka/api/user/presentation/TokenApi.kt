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
    private val tokenUserService: TokenUserService
) : TokenApiSpecification {
    @PostMapping
    override fun createToken(@RequestBody request: CreateTokenRequest): ResponseEntity<CreateTokenResponse> {
        val tokens = tokenUserService.createToken(request.userTsid)
        return ResponseEntity
            .ok(CreateTokenResponse(tokens.accessToken, tokens.refreshToken))
    }

    @PostMapping("/peek")
    override fun peekToken(@RequestBody request: PeekTokenRequest): ResponseEntity<PeekTokenResponse> {
        val result = tokenUserService.peekToken(request.tokenId)
        return ResponseEntity
            .ok(PeekTokenResponse(result, LocalDateTime.now()))
    }
}