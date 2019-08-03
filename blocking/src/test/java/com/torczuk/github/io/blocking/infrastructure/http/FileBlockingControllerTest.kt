package com.torczuk.github.io.blocking.infrastructure.http

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FileBlockingControllerTest(@Autowired val testRestTemplate: TestRestTemplate) {

    @Test
    fun `should download file`() {
        val resp = testRestTemplate.getForEntity("/api/v1/blocking/files", ByteArray::class.java)
        Assertions.assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
    }
}