package com.torczuk.github.io.blocking.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyExchangeBlockingController {
    private val logger = LoggerFactory.getLogger(CurrencyExchangeBlockingController::class.java)

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun stats(@RequestParam delay: Long?): ResponseEntity<CurrencyExchange> {
        logger.info("exchange with delay")
        Thread.sleep(delay ?: 200)
        return ResponseEntity.ok(CurrencyExchange("USD", "SGD", BigDecimal("1.3")))
    }
}
