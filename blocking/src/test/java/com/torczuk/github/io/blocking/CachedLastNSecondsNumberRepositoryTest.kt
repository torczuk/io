package com.torczuk.github.io.blocking

import com.torczuk.github.io.blocking.domain.Statistic
import com.torczuk.github.io.blocking.infrastructure.db.CachedLastNSecondsNumberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Clock
import java.time.Duration

class CachedLastNSecondsNumberRepositoryTest {

    @Test
    fun `should cache last second of result`() {
        val clock = Clock.systemDefaultZone()
        val cachedBy = Duration.ofSeconds(1)
        val cache = CachedLastNSecondsNumberRepository(clock, cachedBy)

        cache.add(BigDecimal.TEN)
        cache.add(BigDecimal.TEN)
        cache.add(BigDecimal.TEN)

        val stats = cache.getAllLast()
        assertThat(stats).isEqualTo(Statistic(3, BigDecimal("10.00")))

        Thread.sleep(cachedBy.toMillis())
        val afterEvictionStats = cache.getAllLast()
        assertThat(afterEvictionStats).isEqualTo(Statistic.zero)

        cache.add(BigDecimal.TEN)
        val newStats = cache.getAllLast()
        assertThat(newStats).isEqualTo(Statistic(1, BigDecimal("10.00")))
    }
}

