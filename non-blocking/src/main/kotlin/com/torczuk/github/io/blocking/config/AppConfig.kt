package com.torczuk.github.io.blocking.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.torczuk.github.io.blocking.infrastructure.db.CachedLastNSecondsNumberRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.time.Clock
import java.time.Duration

@EnableWebFlux
@Configuration
class AppConfig: WebFluxConfigurer {

    @Bean
    fun clock() = Clock.systemUTC()

    @Bean
    fun repository() = CachedLastNSecondsNumberRepository(clock(), Duration.ofMinutes(1))

    @Bean
    fun objecMapper() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
    }
}