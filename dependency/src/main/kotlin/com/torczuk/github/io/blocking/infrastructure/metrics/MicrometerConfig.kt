package com.torczuk.github.io.blocking.infrastructure.metrics

import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MicrometerConfig(@Value("\${application.environment}") val environment: String) {

    private val logger = LoggerFactory.getLogger(MicrometerConfig::class.java)

    @Bean
    fun meterRegistryCustomizer(): MeterRegistryCustomizer<MeterRegistry> {
        logger.info("Configuring micrometer for $environment")
        return MeterRegistryCustomizer { registry: MeterRegistry ->
            registry.config()
                    .commonTags("env", environment)
        }
    }

}