package io.ticketaka.api.common.infrastructure.tsid

import com.github.f4b6a3.tsid.TsidCreator

class TsIdKeyGenerator {
    companion object {
        fun next(prefix: String): String {
            if (prefix.contains("-")) {
                prefix.replace("-", "")
            }
            return TsidCreator.getTsid().format("$prefix-%S")
        }
    }
}
