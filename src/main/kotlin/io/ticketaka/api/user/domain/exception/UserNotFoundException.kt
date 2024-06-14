package io.ticketaka.api.user.domain.exception

class UserNotFoundException(message: String = "사용자를 찾을 수 없습니다.") : RuntimeException(message)
