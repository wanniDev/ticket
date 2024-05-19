package io.ticketaka.api.reservation.application

import io.ticketaka.api.TestContainerRegistry
import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
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
                    userTsid = "usr-576023809841191373",
                    date = LocalDate.of(2024, 5, 4),
                    seatNumbers = listOf("A1"),
                )
            val executor = Executors.newFixedThreadPool(10)
            val reservationCnt = AtomicInteger(0)
            val failedCnt = AtomicInteger(0)

            // when
            (1..len).forEach { i ->
                executor.submit {
                    try {
                        reservationService.createReservation(createReservationCommand)
                        reservationCnt.addAndGet(1)
                    } catch (e: Exception) {
                        if (e is BadClientRequestException) {
                            failedCnt.addAndGet(1)
                        }
                    }
                }
            }

            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.MINUTES)

            // then
            assert(reservationCnt.get() == 1)
            assert(failedCnt.get() == len - reservationCnt.get())
        }
    }
