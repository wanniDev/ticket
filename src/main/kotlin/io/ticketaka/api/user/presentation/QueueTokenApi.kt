package io.ticketaka.api.user.presentation

import io.ticketaka.api.user.application.QueueTokenUserService
import io.ticketaka.api.user.presentation.dto.CreateTokenRequest
import io.ticketaka.api.user.presentation.dto.CreateTokenResponse
import io.ticketaka.api.user.presentation.dto.PeekTokenRequest
import io.ticketaka.api.user.presentation.dto.PeekTokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/token")
class QueueTokenApi(
    private val queueTokenUserService: QueueTokenUserService,
) : QueueTokenApiSpecification {
    @PostMapping
    override fun createToken(
        @RequestBody request: CreateTokenRequest,
    ): ResponseEntity<CreateTokenResponse> {
        val createdTokenTsid = queueTokenUserService.createToken(request.userId)
        return ResponseEntity
            .ok(CreateTokenResponse(createdTokenTsid))
    }

    @PostMapping("/peek")
    override fun peekToken(
        @RequestBody peekTokenRequest: PeekTokenRequest,
    ): ResponseEntity<PeekTokenResponse> {
        val result = queueTokenUserService.peekToken(peekTokenRequest.tokenId)
        return ResponseEntity
            .ok(PeekTokenResponse(result, LocalDateTime.now()))
    }
}
