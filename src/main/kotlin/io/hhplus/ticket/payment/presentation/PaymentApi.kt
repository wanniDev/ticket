package io.hhplus.ticket.payment.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal

@RequestMapping("/api/payment")
class PaymentApi {
    @PostMapping("/recharge")
    fun recharge(@RequestBody request: RechargeRequest): ResponseEntity<RechargeResponse> {
        return ResponseEntity.ok(RechargeResponse("userId", request.amount))
    }

    @GetMapping("/balance")
    fun getBalance(@RequestParam userId: String): ResponseEntity<BalanceResponse> {
        return ResponseEntity.ok(BalanceResponse("userId", BigDecimal(10000)))
    }
}