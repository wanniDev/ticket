package io.ticketaka.api.common.infrastructure.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ticketaka.api.user.domain.Token
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CaffeineCacheConfig {
    @Bean
    fun tokenCache(): Cache<String, Token> {
        return Caffeine.newBuilder()
            .initialCapacity(500)
            .weakKeys()
            .weakValues()
            .recordStats()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(500)
            .build()
    }

    @Bean
    fun caffeineConfig(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(100)
            .weakKeys()
            .weakValues()
            .recordStats()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(100)
    }

    @Bean
    fun cacheManager(caffeine: Caffeine<Any, Any>): CaffeineCacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(caffeine)
        return caffeineCacheManager
    }
}
