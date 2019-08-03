package com.torczuk.github.io.blocking

import com.torczuk.github.io.blocking.infrastructure.db.InMemoryNumberRepository
import com.torczuk.github.io.blocking.domain.Statistic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@ExtendWith(MockitoExtension::class)
class InMemoryNumberRepositoryTest {

    @Mock
    lateinit var clock: Clock
    lateinit var cache: ConcurrentMap<Long, Statistic>
    lateinit var repo: InMemoryNumberRepository


    @BeforeEach
    internal fun setUp() {
        cache = ConcurrentHashMap()
        repo = InMemoryNumberRepository(clock, cache)
    }

    @Test
    fun `should return zero when there is no data`() {
        val stats = repo.getAllLast()

        assertThat(stats).isEqualTo(Statistic.zero)
    }

    @Test
    fun `should return aggregated value for single element when request is in defined window`() {
        given(clock.millis()).willReturn(toMillis("2019-11-11 12:00:00.001"))
        repo.add(TEN)

        val stats = repo.getAllLast()

        assertThat(stats).isEqualTo(Statistic(1, TEN.setScale(2)))
    }

    @Test
    fun `should return aggregated value for more than one element when request is in defined window`() {
        given(clock.millis()).willReturn(toMillis("2019-11-11 12:00:00.100"))
        repo.add(TEN)
        repo.add(BigDecimal(20))
        repo.add(BigDecimal(30))

        val stats = repo.getAllLast()

        assertThat(stats).isEqualTo(Statistic(3, BigDecimal("20.00")))
    }

    @Test
    fun `should skip elements when they are not in requested window`() {
        given(clock.millis()).willReturn(toMillis("2019-11-11 12:00:59.000"))
        repo.add(BigDecimal(10))
        given(clock.millis()).willReturn(toMillis("2019-11-11 12:02:00.000"))
        repo.add(BigDecimal(20))
        given(clock.millis()).willReturn(toMillis("2019-11-11 12:02:59.000"))
        repo.add(BigDecimal(30))

        val stats = repo.getAllLast()

        assertThat(stats).isEqualTo(Statistic(2, BigDecimal("25.00")))
    }

    private fun toMillis(date: String, format: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm:ss.SSS")): Long {
        return LocalDateTime.parse(date, format).toInstant(ZoneOffset.UTC).toEpochMilli()
    }
}
