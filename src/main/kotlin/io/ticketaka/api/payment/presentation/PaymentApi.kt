package io.ticketaka.api.payment.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payment")
class PaymentApi: PaymentApiSpecification {

    @PostMapping
    override fun pay(@RequestBody request: PayRequest): ResponseEntity<Void> {
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