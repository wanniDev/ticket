package io.ticketaka.api.point.presentation

import io.ticketaka.api.common.infrastructure.aop.OnMap
import io.ticketaka.api.point.application.PointBalanceService
import io.ticketaka.api.point.presentation.dto.BalanceResponse
import io.ticketaka.api.point.presentation.dto.RechargeRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        val userPoint = pointBalanceService.getBalance(userTsid)
        return ResponseEntity.ok(BalanceResponse(userPoint.userTsid, userPoint.balance))
    }
}
