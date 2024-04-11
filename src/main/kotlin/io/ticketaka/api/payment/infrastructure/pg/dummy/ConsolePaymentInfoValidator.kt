package io.ticketaka.api.payment.infrastructure.pg.dummy

import io.ticketaka.api.payment.domain.PaymentInfoData
import io.ticketaka.api.payment.domain.PaymentInfoValidator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ConsolePaymentInfoValidator: PaymentInfoValidator {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun validate(paymentInfoData: PaymentInfoData) {
        log.info("paymentInfo validator invoked!")
    }
}