package io.ticketaka.api.token.application

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.application.TokenUserQueryService
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.User
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
class TokenUserServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val point = Point.newInstance()
        val user =
            User("userTsid1", point)
        user.id = 1

        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(any()) } doReturn user
            }
        val tokenUserService = TokenUserService(mock(), mockTokenUserQueryService, mock())

        // when
        val createToken = tokenUserService.createToken("userTsid1")

        // then
        assertThat(createToken).isNotNull
    }

    @Test
    fun `when user not found then throw exception`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingMap>()
        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(any()) } doThrow NotFoundException("사용자를 찾을 수 없습니다.")
            }
        val tokenUserService = TokenUserService(mockTokenWaitingQueue, mockTokenUserQueryService, mock())

        // when
        assertThrows<NotFoundException> {
            tokenUserService.createToken("userTsid1")
        }

        // then
        verify(mockTokenUserQueryService).getUser("userTsid1")
    }

    @Test
    fun `peekToken returns true when the order of the token queue matches`() {
        // given
        val mockTokenWaitingMap =
            mock<TokenWaitingMap> {
                on { size() } doReturn 1
            }
        val tokenUserService = TokenUserService(mockTokenWaitingMap, mock(), mock())

        // when
        tokenUserService.peekToken()
        // then
        verify(mockTokenWaitingMap).size()
    }

//    @Test
//    fun `peekToken returns false when the order of the token queue does not match`() {
//        // given
//        val userId1 = 1L
//        val userId2 = 2L
//        val tokenPosition0 = Token.newInstance(userId1)
//        val tokenPosition1 = Token.newInstance(userId2)
//        val tokenWaitingQueue =
//            mock<TokenWaitingQueue> {
//                on { peek() } doReturn tokenPosition0
//            }
//        val tokenUserService = TokenUserService(tokenWaitingQueue, mock(), mock())
//
//        // when
//        val peekToken = tokenUserService.peekToken(tokenPosition1.tsid)
//
//        // then
//        verify(tokenWaitingQueue).peek()
//        assertFalse(peekToken)
//    }
}
