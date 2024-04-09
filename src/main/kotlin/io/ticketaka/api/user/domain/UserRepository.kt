package io.ticketaka.api.user.domain

interface UserRepository {
    fun findByTsid(userTsid: String): User?
}