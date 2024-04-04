package io.hhplus.ticket.payment.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/payment")
class PaymentApi {
    @PostMapping("/recharge")
    fun recharge(@RequestBody request: RechargeRequest): ResponseEntity<RechargeResponse> {
        return ResponseEntity.ok(RechargeResponse("userId", request.amount))
    }
}