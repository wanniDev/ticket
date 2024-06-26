package io.ticketaka.api.reservation.application

import io.ticketaka.api.TestContainerRegistry
import io.ticketaka.api.common.exception.ReservationStateException
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class ReservationConcurrencyTest
    @Autowired
    constructor(
        private val reservationService: ReservationService,
    ) : TestContainerRegistry() {
        @Test
        fun `only first reservation will success`() {
            val len = 10
            val createReservationCommand =
                CreateReservationCommand(
                    userId = 1L,
                    date = LocalDate.of(2024, 5, 4),
                    seatNumbers = listOf("A1"),
                )
            val executor = Executors.newFixedThreadPool(10)
            val reservationCnt = AtomicInteger(0)
            val failedCnt = AtomicInteger(0)

            // when
            (1..len).forEach { _ ->
                executor.submit {
                    try {
                        reservationService.createReservation(createReservationCommand)
                        reservationCnt.addAndGet(1)
                    } catch (e: Exception) {
                        if (e is ReservationStateException) {
                            failedCnt.addAndGet(1)
                        }
                    }
                }
            }

            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.MINUTES)

            // then
            assertThat(reservationCnt.get() == 1)
            assertThat(failedCnt.get() == len - reservationCnt.get())
        }
    }
