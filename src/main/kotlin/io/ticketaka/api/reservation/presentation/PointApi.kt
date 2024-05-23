package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.common.infrastructure.aop.OnMap
import io.ticketaka.api.reservation.application.PointBalanceService
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
@RequestMapping("/api/point")
class PointApi(
    private val pointBalanceService: PointBalanceService,
) : PointApiSpecification {
    @OnMap
    @PostMapping("/recharge")
    override fun recharge(
        @RequestBody request: RechargeRequest,
    ): ResponseEntity<Void> {
        pointBalanceService.recharge(request.toCommand())
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/balance")
    override fun getBalance(
        @RequestParam userTsid: String,
    ): ResponseEntity<BalanceResponse> {
        pointBalanceService.getBalance(userTsid)
        return ResponseEntity.ok(BalanceResponse("userId", BigDecimal(10000)))
    }
}
