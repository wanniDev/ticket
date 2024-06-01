package io.ticketaka.api.point.application

import io.ticketaka.api.common.infrastructure.IdempotentKeyGenerator
import io.ticketaka.api.reservation.domain.point.Idempotent
import io.ticketaka.api.reservation.domain.point.IdempotentRepository
import io.ticketaka.api.reservation.domain.point.PointHistory
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class IdempotentService(
    private val idempotentRepository: IdempotentRepository,
) {
    fun save(idempotent: Idempotent): Idempotent {
        return idempotentRepository.save(idempotent)
    }

    fun findIdempotentKey(
        userTsid: String,
        tokenTsid: String,
        amount: BigDecimal,
        transactionType: PointHistory.TransactionType,
    ): Idempotent? {
        val key = IdempotentKeyGenerator.generate(userTsid, tokenTsid, amount, transactionType.name)
        return idempotentRepository.findByKey(key)
    }
}
