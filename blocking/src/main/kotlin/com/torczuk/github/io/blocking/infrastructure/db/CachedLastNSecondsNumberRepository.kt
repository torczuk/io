package com.torczuk.github.io.blocking.infrastructure.db

import com.google.common.cache.CacheBuilder.newBuilder
import com.torczuk.github.io.blocking.domain.NumberRepository
import com.torczuk.github.io.blocking.domain.Statistic
import java.math.BigDecimal
import java.time.Clock
import java.time.Duration

class CachedLastNSecondsNumberRepository(clock: Clock,
                                         cachedBy: Duration) : NumberRepository {

    private val nSecondsCache = newBuilder().expireAfterWrite(cachedBy).build<Long, Statistic>()
    private val repo = InMemoryNumberRepository(clock = clock, cache = nSecondsCache.asMap())

    override fun add(number: BigDecimal) = repo.add(number)
    override fun getAllLast(duration: Duration) = repo.getAllLast(duration)
}