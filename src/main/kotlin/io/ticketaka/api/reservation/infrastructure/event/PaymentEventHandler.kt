package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.point.domain.payment.PaymentApprovalEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PaymentEventHandler {
    private val logger = LoggerFactory.getLogger("payment")

    @EventListener
    fun handle(event: PaymentApprovalEvent) {
        // pg 승인이후 후속처리 이벤트 발생. 실제로 pg 연동은 안하고 있으므로 로그 출력만 수행한다.
        logger.debug("PaymentApprovalEvent: {}", event)
    }
}
