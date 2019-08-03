package com.torczuk.github.io.blocking.domain

import java.math.BigDecimal
import java.time.Duration

interface NumberRepository {

    companion object {
        val minute = Duration.ofMinutes(1)
    }

    fun add(number: BigDecimal): Unit
    fun getAllLast(duration: Duration = minute): Statistic
}