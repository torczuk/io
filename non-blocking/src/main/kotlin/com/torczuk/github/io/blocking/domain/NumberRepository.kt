package com.torczuk.github.io.blocking.domain

import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.Duration

interface NumberRepository {

    companion object {
        val minute = Duration.ofMinutes(1)
    }

    fun add(number: BigDecimal): Mono<Boolean>
    fun getAllLast(duration: Duration = minute): Mono<Statistic>
}