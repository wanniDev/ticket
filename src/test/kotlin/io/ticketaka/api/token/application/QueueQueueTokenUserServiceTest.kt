package io.ticketaka.api.token.application

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.user.application.QueueTokenUserCacheAsideQueryService
import io.ticketaka.api.user.application.QueueTokenUserService
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.token.QueueToken
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class QueueQueueTokenUserServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val point = Point.newInstance()
        val user =
            User.newInstance(point.id)

        val mockQueueTokenUserCacheAsideQueryService =
            mock<QueueTokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }
        val queueTokenUserService = QueueTokenUserService(mock(), mockQueueTokenUserCacheAsideQueryService, mock())

        // when
        val createToken = queueTokenUserService.createToken(user.id)

        // then
        assertThat(createToken).isNotNull
    }

    @Test
    fun `when user not found then throw exception`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingMap>()
        val mockQueueTokenUserCacheAsideQueryService =
            mock<QueueTokenUserCacheAsideQueryService> {
                on { getUser(any()) } doThrow NotFoundException("사용자를 찾을 수 없습니다.")
            }
        val queueTokenUserService = QueueTokenUserService(mockTokenWaitingQueue, mockQueueTokenUserCacheAsideQueryService, mock())

        // when
        assertThrows<NotFoundException> {
            queueTokenUserService.createToken(any())
        }

        // then
        verify(mockQueueTokenUserCacheAsideQueryService).getUser(any())
    }

    @Test
    fun `peekToken returns true when the order of the token queue matches`() {
        // given
        val userId = 1L
        val queueTokenPosition0 = QueueToken.newInstance(userId)
        val mockTokenWaitingMap =
            mock<TokenWaitingMap> {
                on { get(queueTokenPosition0.id) } doReturn queueTokenPosition0
            }
        val queueTokenUserService = QueueTokenUserService(mockTokenWaitingMap, mock(), mock())

        // when
        queueTokenUserService.peekToken(queueTokenPosition0.id)
        // then
        verify(mockTokenWaitingMap).get(queueTokenPosition0.id)
    }
}
