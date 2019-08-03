package com.torczuk.github.io.blocking.infrastructure.db

import com.torczuk.github.io.blocking.domain.NumberRepository
import com.torczuk.github.io.blocking.domain.Statistic
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.Clock
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryNumberRepository(val clock: Clock,
                               val cache: ConcurrentMap<Long, Statistic> = ConcurrentHashMap())
    : NumberRepository {

    val logger = LoggerFactory.getLogger(InMemoryNumberRepository::class.java)

    override fun add(number: BigDecimal) {
        val seconds = clock.epochTime()
        val statistic = Statistic(1, number)
        cache.compute(seconds) { k, v ->
            when (v) {
                null -> statistic
                else -> statistic + v
            }
        }
        logger.debug("{}", cache)
    }

    override fun getAllLast(duration: Duration): Statistic {
        val seconds = clock.epochTime()
        val add: (a: Statistic, b: Statistic) -> Statistic = { a, b -> a + b }
        val interval = duration.seconds

        val sum = cache.filterKeys { k -> k <= seconds && seconds - interval <= k }.values.fold(Statistic.zero, add)
        return Statistic(sum.items, sum.avg())
    }
}

private fun Clock.epochTime() = this.millis() / 1000
