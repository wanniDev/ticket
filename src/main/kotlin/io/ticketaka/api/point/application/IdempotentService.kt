package io.ticketaka.api.point.application

import io.ticketaka.api.common.infrastructure.IdempotentKeyGenerator
import io.ticketaka.api.point.domain.Idempotent
import io.ticketaka.api.point.domain.IdempotentRepository
import io.ticketaka.api.point.domain.PointHistory
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
        userId: Long,
        tokenId: Long,
        amount: BigDecimal,
        transactionType: PointHistory.TransactionType,
    ): Idempotent? {
        val key = IdempotentKeyGenerator.generate(userId, tokenId, amount, transactionType.name)
        return idempotentRepository.findByKey(key)
    }
}
