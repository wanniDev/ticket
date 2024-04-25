package io.ticketaka.api.common.infrastructure.aop

import org.springframework.stereotype.Indexed

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Indexed
annotation class OnQueue()
