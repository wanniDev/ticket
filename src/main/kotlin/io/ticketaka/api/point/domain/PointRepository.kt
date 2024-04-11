package io.ticketaka.api.point.domain

interface PointRepository {
    fun save(point: Point): Point
    fun findByTsid(tsid: String): Point?
}