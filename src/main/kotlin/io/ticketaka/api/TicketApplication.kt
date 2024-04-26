package io.ticketaka.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.Environment

@SpringBootApplication
class TicketApplication(private val env: Environment) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TicketApplication>(*args)
        }
    }
}
