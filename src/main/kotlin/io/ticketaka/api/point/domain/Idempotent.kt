package io.ticketaka.api.point.domain

import io.ticketaka.api.common.infrastructure.IdempotentKeyGenerator
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "idempotent")
class Idempotent(
    @Id
    var id: Long,
    val key: String,
) {
    companion object {
        fun newInstance(
            userId: Long,
            tokenId: Long,
            amount: BigDecimal,
            transactionType: PointHistory.TransactionType,
        ): Idempotent {
            return Idempotent(TsIdKeyGenerator.nextLong(), IdempotentKeyGenerator.generate(userId, tokenId, amount, transactionType.name))
        }
    }
}
