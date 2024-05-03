package io.ticketaka.api.point.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.reservation.application.BalanceService
import io.ticketaka.api.reservation.application.PaymentService
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.application.dto.RechargeCommand
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class BalanceServiceTest {
    @Test
    fun recharge() {
        // given
        val point = Point.newInstance()
        point.id = 1
        val user = User("userTsid1", point)
        user.id = 1
        val token = Token.newInstance(user)
        val rechargeCommand =
            RechargeCommand(
                user.tsid,
                token.tsid,
                20000.toBigDecimal(),
            )
        val paymentCommand =
            PaymentCommand(
                userTsid = user.tsid,
                pointTsid = point.tsid,
                amount = rechargeCommand.amount,
            )

        val mockUserRepository =
            mock<UserRepository> {
                on { findByTsid(any()) } doReturn user
            }
        val mockPaymentService = mock<PaymentService>()
        val balanceService = BalanceService(mockUserRepository, mockPaymentService, mock())

        // when
        balanceService.recharge(rechargeCommand)

        // then
        verify(mockPaymentService).paymentApproval(paymentCommand)
    }

    @Test
    fun `if user try to recharge with negative amount, then throw BadClientRequestException`() {
        // given
        val point = Point.newInstance()
        point.id = 1
        val user = User("userTsid1", point)
        user.id = 1
        val token = Token.newInstance(user)
        val rechargeCommand =
            RechargeCommand(
                user.tsid,
                token.tsid,
                (-20000).toBigDecimal(),
            )

        val mockUserRepository =
            mock<UserRepository> {
                on { findByTsid(any()) } doReturn user
            }
        val balanceService = BalanceService(mockUserRepository, mock(), mock())

        // when
        val exception =
            assertFailsWith<BadClientRequestException> {
                balanceService.recharge(rechargeCommand)
            }

        // then
        assertEquals("충전 금액은 0보다 커야 합니다.", exception.message)
    }

    @Test
    fun getBalance() {
        // given
        val point = Point.newInstance()
        val user = User("userTsid1", point)
        val mockUserRepository =
            mock<UserRepository> {
                on { findByTsid(any()) } doReturn user
            }
        val balanceService = BalanceService(mockUserRepository, mock(), mock())

        // when
        val balanceQueryModel = balanceService.getBalance(user.tsid)

        // then
        assertEquals(user.tsid, balanceQueryModel.userTsid)
        assertEquals(point.balance, balanceQueryModel.balance)
    }
}
