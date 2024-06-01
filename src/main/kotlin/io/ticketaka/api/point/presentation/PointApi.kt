package io.ticketaka.api.point.presentation

import io.ticketaka.api.common.infrastructure.aop.OnMap
import io.ticketaka.api.point.application.PointService
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
    private val pointService: PointService,
) : PointApiSpecification {
    @OnMap
    @PostMapping("/recharge")
    override fun recharge(
        @RequestBody request: RechargeRequest,
    ): ResponseEntity<Void> {
        pointService.recharge(request.toCommand())
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/balance")
    override fun getBalance(
        @RequestParam userId: Long,
    ): ResponseEntity<BalanceResponse> {
        val userPoint = pointService.getBalance(userId)
        return ResponseEntity.ok(BalanceResponse(userPoint.userId, userPoint.balance))
    }
}
