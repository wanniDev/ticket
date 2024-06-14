package io.ticketaka.api.user.domain.exception

class AlreadyTokenExpiredException(message: String = "토큰이 만료되었습니다.") : RuntimeException(message)
