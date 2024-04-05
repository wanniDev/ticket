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
    fun getBalance(@RequestParam userId: String): ResponseEntity<BalanceResponse> {
        /**
         * 1. 사용자 id 검증
         * 2. 사용자 잔액 조회
         * 3. 응답
         */
        return ResponseEntity.ok(BalanceResponse("userId", BigDecimal(10000)))
    }

    @PostMapping
    fun pay(@RequestBody request: PayRequest): ResponseEntity<Void> {
        /**
         * 1. 요청 입력값 검증
         * 2. 사용자 id 검증
         * 3. 예약 id 검증
         * 4. 결제 승인 호출
         * 5. 예약 상태 업데이트
         * 6. 응답
         */
        return ResponseEntity.ok().build()
    }
}