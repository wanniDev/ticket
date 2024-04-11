package io.ticketaka.api.common.domain.queue

import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.user.domain.User
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
        val point = Point.newInstance()
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { offer(any()) } doReturn true
        }
        val token = Token.newInstance(User("userTsid1", point))

        // when
        val result = mockTokenWaitingQueue.offer(token)

        // then
        assertTrue(result)
    }

    @Test
    fun `test poll`() {
        // given
        val point = Point.newInstance()
        val mockTokenWaitingQueue = mock<TokenWaitingQueue> {
            on { poll() } doReturn Token.newInstance(User("userTsid1", point))
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