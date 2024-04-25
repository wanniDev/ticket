package io.ticketaka.api.reservation.domain.point

interface PointRepository {
    fun save(point: Point): Point

    fun findByTsid(tsid: String): Point?
}
