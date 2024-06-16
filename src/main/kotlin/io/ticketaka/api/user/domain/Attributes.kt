package io.ticketaka.api.user.domain

data class Attributes(
    val mainAttributes: Map<String, Any>,
    val subAttributes: Map<String, Any>,
    val otherAttributes: Map<String, Any>,
)
