package io.ticketaka.api.common.domain.queue

import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.token.QueueToken
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExtendWith(MockitoExtension::class)
class QueueTokenWaitingQueueTest {
    @Test
    fun `test offer`() {
        // given
        val point = Point.newInstance()
        val user = User.newInstance(point.id)
        val mockTokenWaitingQueue =
            mock<TokenWaitingQueue> {
                on { offer(any()) } doReturn true
            }
        val queueToken = QueueToken.newInstance(user.id)

        // when
        val result = mockTokenWaitingQueue.offer(queueToken)

        // then
        assertTrue(result)
    }

    @Test
    fun `test poll`() {
        // given
        val point = Point.newInstance()
        val user = User.newInstance(point.id)
        val mockQueueTokenWaitingQueue =
            mock<TokenWaitingQueue> {
                on { poll() } doReturn QueueToken.newInstance(user.id)
            }

        // when
        val result = mockQueueTokenWaitingQueue.poll()

        // then
        assertTrue(result != null)
    }

    @Test
    fun `when polling an empty queue it will return null`() {
        // given
        val mockTokenWaitingQueue =
            mock<TokenWaitingQueue> {
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
        val mockTokenWaitingQueue =
            mock<TokenWaitingQueue> {
                on { peek() } doReturn null
            }

        // when
        val result = mockTokenWaitingQueue.peek()

        // then
        assertTrue(result == null)
    }
}
