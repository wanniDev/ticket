package io.ticketaka.api.common.infrastructure

import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.util.Base64

class IdempotentKeyGenerator {
    companion object {
        fun generate(
            userTsid: String,
            tokenTsid: String,
            amount: BigDecimal,
            transactionType: String,
        ): String {
            return String(
                Base64.getEncoder()
                    .encode("${userTsid}_${tokenTsid}_${amount}_$transactionType".toByteArray(StandardCharsets.ISO_8859_1)),
            )
        }
    }
}
