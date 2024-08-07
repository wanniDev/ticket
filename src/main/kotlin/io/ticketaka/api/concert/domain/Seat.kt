package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.exception.ReservationStateException
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "seats")
class Seat(
    @Id
    val id: Long,
    val number: String,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val price: BigDecimal,
    val concertId: Long,
    val concertDate: LocalDate,
) : Persistable<Long> {
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    override fun getId(): Long = id

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    fun isAvailable(): Boolean = this.status == Status.AVAILABLE

    fun available() {
        this.status = Status.AVAILABLE
    }

    fun occupy() {
        this.status = Status.OCCUPIED
    }

    fun reserve() {
        if (this.isAvailable()) {
            this.status = Status.RESERVED
        } else {
            throw ReservationStateException("Seat number: ${this.number} is not available")
        }
    }

    fun validateReserved() {
        if (this.status != Status.RESERVED) {
            throw ReservationStateException("Seat number: ${this.number} is not reserved")
        }
    }

    enum class Status {
        AVAILABLE,
        RESERVED,
        OCCUPIED,
    }

    companion object {
        fun newInstance(
            number: String,
            price: BigDecimal,
            concertId: Long,
        ): Seat =
            Seat(
                id = TsIdKeyGenerator.nextLong(),
                number = number,
                status = Status.AVAILABLE,
                price = price,
                concertId = concertId,
                concertDate = LocalDate.now(),
            )
    }
}
