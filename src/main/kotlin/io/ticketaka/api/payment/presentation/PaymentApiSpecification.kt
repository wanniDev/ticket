package io.ticketaka.api.payment.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Payment", description = "결제 도메인 API")
interface PaymentApiSpecification {
    @Operation(
        summary = "결제 API",
        description = "결제를 진행하는 API를 제공합니다."
    )
    @ApiResponse(responseCode = "200", description = "결제 성공")
    fun pay(request: PayRequest): ResponseEntity<Void>
}