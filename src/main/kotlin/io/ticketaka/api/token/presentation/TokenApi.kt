package io.ticketaka.api.token.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/token")
class TokenApi : TokenApiSpecification {
    @PostMapping
    override fun createToken(@RequestBody request: CreateTokenRequest): ResponseEntity<CreateTokenResponse> {
        return ResponseEntity
            .ok(CreateTokenResponse("tokenId", LocalDateTime.now().plusMinutes(30L)))
    }

    @PostMapping("/peek")
    override fun peekToken(@RequestBody request: PeekTokenRequest): ResponseEntity<PeekTokenResponse> {
        return ResponseEntity
            .ok(PeekTokenResponse(true, LocalDateTime.now()))
    }
}