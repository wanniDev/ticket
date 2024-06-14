package io.ticketaka.api.user.domain

import java.util.Arrays

enum class Role {
    USER,
    ;

    companion object {
        fun findByRole(role: String): Role {
            return Arrays.stream(entries.toTypedArray())
                .filter { r -> r.name == role }
                .findFirst()
                .orElseThrow { IllegalArgumentException() }
        }
    }
}
