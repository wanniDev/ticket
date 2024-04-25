package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.reservation.application.BalanceService
import io.ticketaka.api.reservation.presentation.dto.BalanceResponse
import io.ticketaka.api.reservation.presentation.dto.RechargeRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/balance")
class PointApi(
    private val balanceService: BalanceService,
) : PointApiSpecification {
    @PostMapping("/recharge")
    override fun recharge(
        @RequestBody request: RechargeRequest,
    ): ResponseEntity<Void> {
        balanceService.recharge(request.toCommand())
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/balance")
    override fun getBalance(
        @RequestParam userTsid: String,
    ): ResponseEntity<BalanceResponse> {
        balanceService.getBalance(userTsid)
        return ResponseEntity.ok(BalanceResponse("userId", BigDecimal(10000)))
    }
}
