package io.ticketaka.api.common.infrastructure

import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.util.Base64

class IdempotentKeyGenerator {
    companion object {
        fun generate(
            userId: Long,
            tokenId: Long,
            amount: BigDecimal,
            transactionType: String,
        ): String {
            return String(
                Base64.getEncoder()
                    .encode("${userId}_${tokenId}_${amount}_$transactionType".toByteArray(StandardCharsets.ISO_8859_1)),
            )
        }
    }
}
