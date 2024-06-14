package io.ticketaka.api.user.infrastructure.jpa

import io.ticketaka.api.user.domain.token.QueueToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaTokenRepository : JpaRepository<QueueToken, Long> {
    @Query("SELECT t FROM Token t ORDER BY t.issuedTime ASC LIMIT 1")
    fun findFirstTokenOrderByIssuedTimeAscLimit1(): QueueToken?
}
