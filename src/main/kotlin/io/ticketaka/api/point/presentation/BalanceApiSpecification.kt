package io.ticketaka.api.point.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.ticketaka.api.point.presentation.dto.BalanceResponse
import io.ticketaka.api.point.presentation.dto.RechargeRequest
import io.ticketaka.api.point.presentation.dto.RechargeResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Balance", description = "잔고 도메인 API")
interface BalanceApiSpecification {

    @Operation(
        summary = "충전 API",
        description = "사용자의 잔액을 충전하는 API를 제공합니다."
    )
    @ApiResponse(responseCode = "200", description = "충전 성공")
    fun recharge(request: RechargeRequest): ResponseEntity<RechargeResponse>

    @Operation(
        summary = "잔액 조회 API",
        description = "사용자의 잔액을 조회하는 API를 제공합니다."
    )
    @ApiResponse(responseCode = "200", description = "잔액 조회 성공")
    fun getBalance(userId: String): ResponseEntity<BalanceResponse>
}