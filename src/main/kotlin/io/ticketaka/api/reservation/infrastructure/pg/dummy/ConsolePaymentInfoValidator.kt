package io.ticketaka.api.reservation.infrastructure.pg.dummy

import io.ticketaka.api.reservation.domain.payment.PaymentInfoData
import io.ticketaka.api.reservation.domain.payment.PaymentInfoValidator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ConsolePaymentInfoValidator: PaymentInfoValidator {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun validate(paymentInfoData: PaymentInfoData) {
        log.info("paymentInfo validator invoked!")
    }
}