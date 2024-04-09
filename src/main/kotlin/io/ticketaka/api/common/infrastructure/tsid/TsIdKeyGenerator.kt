package io.ticketaka.api.common.infrastructure.tsid

import com.github.f4b6a3.tsid.TsidCreator

class TsIdKeyGenerator{
    companion object {
        fun next(prefix: String): String {
            return TsidCreator.getTsid().format("$prefix-%S")
        }
    }
}