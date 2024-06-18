package io.ticketaka.api.user.domain

data class Attributes(
    val mainAttributes: Map<String, Any>,
    val subAttributes: Map<String, Any> = emptyMap(),
    val otherAttributes: Map<String, Any> = emptyMap(),
)
