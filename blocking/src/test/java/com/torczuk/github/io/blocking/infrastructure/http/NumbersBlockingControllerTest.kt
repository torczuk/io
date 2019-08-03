package com.torczuk.github.io.blocking.infrastructure.http

import com.torczuk.github.io.blocking.domain.Statistic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@SpringBootTest(webEnvironment = RANDOM_PORT)
class NumbersBlockingControllerTest(@Autowired val testRestTemplate: TestRestTemplate) {

    @Test
    fun `should POST number and GET stats before cache is evicted`() {
        val firstRes = testRestTemplate.getForEntity("/api/v1/blocking/numbers", Statistic::class.java)
        assertThat(firstRes.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(firstRes.body).isEqualTo(Statistic.zero)

        val number = Number(BigDecimal("200.00"))
        val secondRes = testRestTemplate.postForEntity("/api/v1/blocking/numbers", number, Void::class.java)
        assertThat(secondRes.statusCode).isEqualTo(HttpStatus.ACCEPTED)

        val thirdRes = testRestTemplate.getForEntity("/api/v1/blocking/numbers", Statistic::class.java)
        assertThat(thirdRes.body).isEqualTo(Statistic(1, BigDecimal("200.00")))
    }
}