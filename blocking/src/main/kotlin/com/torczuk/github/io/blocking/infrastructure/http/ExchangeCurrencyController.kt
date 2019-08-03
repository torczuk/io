package com.torczuk.github.io.blocking.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/blocking/exchange")
class ExchangeCurrencyController(
        @Value("\${application.dependency.url}") val dependencyUrl: String,
        @Autowired val restTemplate: RestTemplate) {

    val log = LoggerFactory.getLogger(ExchangeCurrencyController::class.java)

    @GetMapping(path = ["/{value}"])
    fun exchange(@PathVariable value: Int): ResponseEntity<BigDecimal> {
        val response = restTemplate.getForEntity(
                "$dependencyUrl/api/v1/currency?delay=200",
                CurrencyExchange::class.java)
        return ResponseEntity.ok(response.body!!.ratio.multiply(BigDecimal(value)))
    }
}