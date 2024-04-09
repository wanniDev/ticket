package io.ticketaka.api.common.domain.queue

import io.ticketaka.api.token.domain.Token
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TokenWaitingQueueTest {
    @Test
    fun `test offer`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { offer(any()) } doReturn true
        }
        val token = Token(1L, "sdfewWER", LocalDateTime.now(), Token.Status.ACTIVE, "sdferWer")

        // when
        val result = mockTokenWaitingQueue.offer(token)

        // then
        assertTrue(result)
    }

    @Test
    fun `test poll`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { poll() } doReturn Token(1L, "sdfewWER", LocalDateTime.now(), Token.Status.ACTIVE, "sdferWer")
        }

        // when
        val result = mockTokenWaitingQueue.poll()

        // then
        assertTrue(result != null)
    }

    @Test
    fun `when polling an empty queue it will return null`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { poll() } doReturn null
        }

        // when
        val result = mockTokenWaitingQueue.poll()

        // then
        assertTrue(result == null)
    }

    @Test
    fun `when peeking an empty queue it will return null`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { peek() } doReturn null
        }

        // when
        val result = mockTokenWaitingQueue.peek()

        // then
        assertTrue(result == null)
    }
}