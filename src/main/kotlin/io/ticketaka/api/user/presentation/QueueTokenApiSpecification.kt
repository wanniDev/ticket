package io.ticketaka.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.ticketaka.api.user.presentation.dto.CreateTokenRequest
import io.ticketaka.api.user.presentation.dto.CreateTokenResponse
import io.ticketaka.api.user.presentation.dto.PeekTokenRequest
import io.ticketaka.api.user.presentation.dto.PeekTokenResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Token", description = "토큰 도메인 API")
interface QueueTokenApiSpecification {
    @Operation(
        summary = "토큰 대기열 조회 API",
        description = "토큰이 현재 사용자의 차례인지 확인하는 코드입니다.",
    )
    fun peekToken(peekTokenRequest: PeekTokenRequest): ResponseEntity<PeekTokenResponse>

    @Operation(
        summary = "토큰 생성 API",
        description = "토큰을 생성하는 API를 제공합니다.",
    )
    fun createToken(request: CreateTokenRequest): ResponseEntity<CreateTokenResponse>
}
