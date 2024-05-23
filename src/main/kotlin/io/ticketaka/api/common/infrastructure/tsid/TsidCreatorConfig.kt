package io.ticketaka.api.common.infrastructure.tsid

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class TsidCreatorConfig {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun tsidCreator() {
        logger.debug("init tsidCreator")
        System.setProperty("tsidcreator.node", "1") // TODO 다수의 인스턴스로 운영될 경우 인스턴스별로 값이 달라야함
        System.setProperty("tsidcreator.node.count", "256")
        logger.debug(
            "tsidCreator initialized, node : {}, node count : {}",
            System.getProperty("tsidcreator.node"),
            System.getProperty("tsidcreator.node.count"),
        )
    }
}
