package io.ticketaka.api.common.infrastructure.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CaffeineCacheConfig {
    @Bean
    fun pointCache(): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(10)
            .recordStats()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .build()
    }

    @Bean
    fun userCache(): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(10)
            .recordStats()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(100)
            .build()
    }

    @Bean
    fun seatNumberCache(): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(100)
            .recordStats()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(100)
            .build()
    }

    @Bean
    fun concertCache(): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .initialCapacity(10)
            .recordStats()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(10)
            .build()
    }

    @Bean
    fun cacheManager(): CaffeineCacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.registerCustomCache("seatNumbers", seatNumberCache())
        caffeineCacheManager.registerCustomCache("concert", concertCache())
        caffeineCacheManager.registerCustomCache("user", userCache())
        caffeineCacheManager.registerCustomCache("point", pointCache())
        return caffeineCacheManager
    }
}
