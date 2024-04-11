package io.ticketaka.api.common

data class ApiError(
    val status: Int,
    val message: String
)
