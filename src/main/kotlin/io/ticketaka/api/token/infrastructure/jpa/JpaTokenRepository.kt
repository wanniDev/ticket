package io.ticketaka.api.token.infrastructure.jpa

import io.ticketaka.api.token.domain.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaTokenRepository: JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t ORDER BY t.issuedTime ASC LIMIT 1")
    fun findFirstTokenOrderByIssuedTimeAscLimit1(): Token?
}