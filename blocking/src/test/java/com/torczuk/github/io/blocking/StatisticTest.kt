package com.torczuk.github.io.blocking

import com.torczuk.github.io.blocking.domain.Statistic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigDecimal.TEN

class StatisticTest {

    @Test
    fun `should add two statistics`() {
        val some = Statistic(items = 1, sum = TEN)
        val other = Statistic(items = 3, sum = TEN)

        val sum = some + other

        assertThat(sum).isEqualTo(Statistic(4, BigDecimal(20)))
    }

    @Test
    fun `should calculate average based on items and values`() {
        assertThat(Statistic(items = 1, sum = TEN).avg()).isEqualTo(BigDecimal("10.00"))
        assertThat(Statistic(items = 2, sum = TEN).avg()).isEqualTo(BigDecimal("5.00"))
        assertThat(Statistic(items = 3, sum = TEN).avg()).isEqualTo(BigDecimal("3.33"))
    }
}