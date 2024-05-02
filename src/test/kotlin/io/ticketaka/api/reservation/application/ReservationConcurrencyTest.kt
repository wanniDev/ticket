package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class ReservationConcurrencyTest
    @Autowired
    constructor(
        private val reservationService: ReservationService,
    ) {
        @Test
        fun `ensure concurrent reservation once`() {
            val len = 10
            val createReservationCommand =
                CreateReservationCommand(
                    userTsid = "userTsid",
                    date = LocalDate.of(2024, 5, 3),
                    seatNumbers = listOf("A1"),
                )
            val executor = Executors.newFixedThreadPool(10)
            val cnt = AtomicInteger(0)

            // when
            (1..len).forEach { i ->
                executor.submit {
                    try {
                        reservationService.createReservation(createReservationCommand)
                        cnt.addAndGet(1)
                    } catch (e: Exception) {
                        println(e)
                        println("error occurred $i")
                    }
                }
            }

            // then
        }
    }
