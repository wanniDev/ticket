package io.ticketaka.api.reservation.application

import io.ticketaka.api.point.infrastructure.event.AsyncEventLogAppender
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationCreator
import org.springframework.stereotype.Service

@Service
class ReservationEventService(
    private val asyncEventLogAppender: AsyncEventLogAppender,
    private val reservationCreator: ReservationCreator,
) {
    private val warningForRetry = "Retry on failure."
    private val retryFailed = "Retry failed."

    fun appendEventLogInfo(event: ReservationCreateEvent) {
        asyncEventLogAppender.appendInfo(event)
    }

    fun retryableCreateReservation(
        event: ReservationCreateEvent,
        retryCount: Int = 3,
    ) {
        try {
            reservationCreator.fromEvent(event)
        } catch (e: Exception) {
            if (retryCount > 0) {
                asyncEventLogAppender.appendWarning(event, warningForRetry)
                retryableCreateReservation(event, retryCount - 1)
            } else {
                asyncEventLogAppender.appendError(event, retryFailed)
            }
        }
    }
}
