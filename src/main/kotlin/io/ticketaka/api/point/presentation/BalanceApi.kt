package io.ticketaka.api.point.presentation

import io.ticketaka.api.point.presentation.dto.BalanceResponse
import io.ticketaka.api.point.presentation.dto.RechargeRequest
import io.ticketaka.api.point.presentation.dto.RechargeResponse
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
class BalanceApi: BalanceApiSpecification {
    @PostMapping("/recharge")
    override fun recharge(@RequestBody request: RechargeRequest): ResponseEntity<RechargeResponse> {
        /**
         * 1. 요청 입력값 검증
         * 2. 사용자 id 검증
         * 3. 결제 승인 호출
         * 4. 사용자 잔액 업데이트
         * 5. 응답
         * 6. 로깅
         */
        return ResponseEntity.ok(RechargeResponse("userId", request.amount))
    }

    @GetMapping("/balance")
    override fun getBalance(@RequestParam userId: String): ResponseEntity<BalanceResponse> {
        /**
         * 1. 사용자 id 검증
         * 2. 사용자 잔액 조회
         * 3. 응답
         */
        return ResponseEntity.ok(BalanceResponse("userId", BigDecimal(10000)))
    }
}