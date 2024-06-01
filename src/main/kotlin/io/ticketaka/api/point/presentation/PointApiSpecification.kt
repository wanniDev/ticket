package io.ticketaka.api.point.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.ticketaka.api.point.presentation.dto.BalanceResponse
import io.ticketaka.api.point.presentation.dto.RechargeRequest
import org.springframework.http.ResponseEntity

@Tag(name = "Balance", description = "잔고 도메인 API")
interface PointApiSpecification {
    @Operation(
        summary = "충전 API",
        description = "사용자의 잔액을 충전하는 API를 제공합니다.",
    )
    @ApiResponse(responseCode = "204", description = "충전 성공")
    @ApiResponse(responseCode = "400", description = "충전 실패")
    fun recharge(request: RechargeRequest): ResponseEntity<Void>

    @Operation(
        summary = "잔액 조회 API",
        description = "사용자의 잔액을 조회하는 API를 제공합니다.",
    )
    @ApiResponse(responseCode = "200", description = "잔액 조회 성공")
    @ApiResponse(responseCode = "404", description = "사용자 조회 실패")
    fun getBalance(userId: Long): ResponseEntity<BalanceResponse>
}
