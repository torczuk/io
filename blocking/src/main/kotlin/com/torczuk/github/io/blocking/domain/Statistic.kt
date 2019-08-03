package com.torczuk.github.io.blocking.domain

import java.math.BigDecimal

data class Statistic(val items: Int, val sum: BigDecimal) {

    companion object {
        val zero = Statistic(0, BigDecimal.ZERO)
        const val scale = 2
    }

    operator fun plus(other: Statistic): Statistic {
        val items = this.items + other.items
        val sum = this.sum + other.sum
        return Statistic(items, sum)
    }

    fun avg(): BigDecimal {
        return if (items != 0) (sum.setScale(scale) / BigDecimal(items).setScale(scale)) else BigDecimal.ZERO
    }

}