package io.ticketaka.api.user.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.common.infrastructure.jwt.JwtProvider
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenRepository
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TokenUserService(
    private val jwtProvider: JwtProvider,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createToken(userTsId: String): String {
        val user = userRepository.findByTsid(userTsId) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")

        val token = Token.newInstance(user.getId())
//        val rawToken = jwtProvider.generate(user.tsid)
        tokenRepository.save(token)
        return token.tsid
    }

    @Transactional
    fun getUser(tsId: String): User {
        return userRepository.findByTsid(tsId) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
    }

    fun peekToken(tokenId: String): Boolean {
        val queueSize = tokenRepository.count()
        if (queueSize > 500L) {
            throw IllegalArgumentException("대기 중인 토큰이 500개를 초과하였습니다.")
        }
        tokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1().let { token ->
            if (token == null) {
                throw NotFoundException("토큰을 찾을 수 없습니다.")
            }
            return token.tsid == tokenId
        }
    }
}
