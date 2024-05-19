package io.ticketaka.api

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class TestContainerRegistry {
    companion object {
        @JvmStatic
        val MY_SQL_CONTAINER: MySQLContainer<*> =
            MySQLContainer("mysql:8.0").apply {
                start()
            }

        @JvmStatic
        @DynamicPropertySource
        fun configureTestProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { MY_SQL_CONTAINER.jdbcUrl }
            registry.add("spring.datasource.username") { MY_SQL_CONTAINER.username }
            registry.add("spring.datasource.password") { MY_SQL_CONTAINER.password }
            registry.add("spring.jpa.hibernate.ddl-auto") { "validate" }
        }
    }
}
