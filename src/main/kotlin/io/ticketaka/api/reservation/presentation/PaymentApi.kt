package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.reservation.application.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payment")
class PaymentApi(
    private val paymentService: PaymentService
): PaymentApiSpecification {

    @PostMapping
    override fun pay(@RequestBody request: PaymentApprovalRequest): ResponseEntity<Void> {
        paymentService.paymentApproval(request.toCommand())
        return ResponseEntity.ok().build()
    }
}