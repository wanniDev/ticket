package io.ticketaka.api.user.domain.exception

class RefreshTokenNotFoundException(message: String = "리프레시 토큰을 찾을 수 없습니다.") : RuntimeException(message)
