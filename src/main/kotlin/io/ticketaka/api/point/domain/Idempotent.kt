package io.ticketaka.api.point.domain

import io.ticketaka.api.common.infrastructure.IdempotentKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "idempotent")
class Idempotent(
    val key: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(
            userTsid: String,
            tokenTsid: String,
            amount: BigDecimal,
            transactionType: PointHistory.TransactionType,
        ): Idempotent {
            return Idempotent(IdempotentKeyGenerator.generate(userTsid, tokenTsid, amount, transactionType.name))
        }
    }
}