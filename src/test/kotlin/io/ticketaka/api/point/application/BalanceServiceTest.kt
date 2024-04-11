package io.ticketaka.api.point.application

import io.ticketaka.api.payment.application.PaymentService
import io.ticketaka.api.payment.application.dto.PaymentCommand
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class BalanceServiceTest {

    @Test
    fun recharge() {
        // given
        val point = Point.newInstance()
        val user = User("userTsid1", point)
        val rechargeCommand = RechargeCommand(
            user.tsid,
            20000.toBigDecimal(),
            "ord-123456789",
            "cardExpiration",
            "cardCvc",
            2024,
            4,
            "12**",
            "991299-1******",
        )
        val paymentCommand = PaymentCommand(
            userTsid = user.tsid,
            amount = rechargeCommand.amount,
            orderId = rechargeCommand.orderId,
            orderName = rechargeCommand.orderName,
            cardNumber = rechargeCommand.cardNumber,
            cardExpirationYear = rechargeCommand.cardExpirationYear,
            cardExpirationMonth = rechargeCommand.cardExpirationMonth,
            cardPasswordPrefix = rechargeCommand.cardPasswordPrefix,
            customerIdentityNumber = rechargeCommand.customerIdentityNumber
        )

        val mockUserRepository = mock<UserRepository> {
            on { findByTsid(any()) } doReturn user
        }
        val mockPaymentService = mock<PaymentService>()
        val balanceService = BalanceService(mockUserRepository, mockPaymentService)

        // when
        balanceService.recharge(rechargeCommand)

        // then
        verify(mockPaymentService).paymentApproval(paymentCommand)
    }

    @Test
    fun getBalance() {
        // given
        val point = Point.newInstance()
        val user = User("userTsid1", point)
        val mockUserRepository = mock<UserRepository> {
            on { findByTsid(any()) } doReturn user
        }
        val balanceService = BalanceService(mockUserRepository, mock())

        // when
        val balanceQueryModel = balanceService.getBalance(user.tsid)

        // then
        assertEquals(user.tsid, balanceQueryModel.userTsid)
        assertEquals(point.balance, balanceQueryModel.balance)
    }
}