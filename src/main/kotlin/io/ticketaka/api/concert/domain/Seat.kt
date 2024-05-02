package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "seats")
class Seat(
    @Id
    val tsid: String,
    val number: String,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val price: BigDecimal,
    @ManyToOne(targetEntity = Concert::class, optional = false, fetch = FetchType.LAZY)
    val concert: Concert,
) {
    fun isAvailable(): Boolean {
        return this.status == Status.AVAILABLE
    }

    fun occupy() {
        this.status = Status.OCCUPIED
    }

    fun reserve() {
        if (this.isAvailable()) {
            this.status = Status.RESERVED
        } else {
            throw IllegalStateException("Seat number: ${this.number} is not available")
        }
    }

    var id: Long? = null

    enum class Status {
        AVAILABLE,
        RESERVED,
        OCCUPIED,
    }

    companion object {
        fun newInstance(
            number: String,
            price: BigDecimal,
            concert: Concert,
        ): Seat {
            return Seat(
                tsid = TsIdKeyGenerator.next("st"),
                number = number,
                status = Status.AVAILABLE,
                price = price,
                concert = concert,
            )
        }
    }
}
