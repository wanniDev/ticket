package io.ticketaka.api.user.infrastructure.oauth2.converter

interface ProviderUserConverter<T, R> {
    fun convert(t: T): R?
}
