package io.ticketaka.api.point.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointBalanceCacheUpdater
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class PointServiceTest {
    @Test
    fun recharge() {
        // given
        val point = Point.newInstance()
        val user = User.newInstance(point.id)
        user.pointId = point.id
        val rechargeCommand =
            RechargeCommand(
                user.id,
                point.id,
                20000.toBigDecimal(),
            )

        val tokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }

        val pointCacheAsideQueryService =
            mock<PointCacheAsideQueryService> {
                on { getPoint(any()) } doReturn point
            }

        val pointBalanceCacheUpdater =
            mock<PointBalanceCacheUpdater> {
                on { recharge(point.id, rechargeCommand.amount) } doAnswer {
                    point.recharge(rechargeCommand.amount)
                }
            }

        val pointService =
            PointService(tokenUserCacheAsideQueryService, pointCacheAsideQueryService, pointBalanceCacheUpdater, mock(), mock())

        // when
        pointService.recharge(rechargeCommand)

        // then
        assertEquals(20000.toBigDecimal(), point.balance)
    }

    @Test
    fun `if user try to recharge with negative amount, then throw BadClientRequestException`() {
        // given
        val point = Point.newInstance()
        val user = User.newInstance(point.id)
        val rechargeCommand =
            RechargeCommand(
                user.id,
                point.id,
                (-20000).toBigDecimal(),
            )

        val tokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }

        val pointCacheAsideQueryService =
            mock<PointCacheAsideQueryService> {
                on { getPoint(any()) } doReturn point
            }
        val pointBalanceCacheUpdater =
            mock<PointBalanceCacheUpdater> {
                on { recharge(point.id, rechargeCommand.amount) } doAnswer {
                    point.recharge(rechargeCommand.amount)
                }
            }

        val pointService =
            PointService(tokenUserCacheAsideQueryService, pointCacheAsideQueryService, pointBalanceCacheUpdater, mock(), mock())

        // when
        val exception =
            assertFailsWith<BadClientRequestException> {
                pointService.recharge(rechargeCommand)
            }

        // then
        assertEquals("충전 금액은 0보다 커야 합니다.", exception.message)
    }

    @Test
    fun getBalance() {
        // given
        val point = Point.newInstance()
        val user = User.newInstance(point.id)
        val tokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }
        val pointCacheAsideQueryService =
            mock<PointCacheAsideQueryService> {
                on { getPoint(any()) } doReturn point
            }
        val pointBalanceCacheUpdater = mock<PointBalanceCacheUpdater>()
        val pointService =
            PointService(tokenUserCacheAsideQueryService, pointCacheAsideQueryService, pointBalanceCacheUpdater, mock(), mock())

        // when
        val balanceQueryModel = pointService.getBalance(user.id)

        // then
        assertEquals(user.id, balanceQueryModel.userId)
        assertEquals(point.balance, balanceQueryModel.balance)
    }
}
