package com.torczuk.github.io.blocking.infrastructure.http

import com.torczuk.github.io.blocking.domain.Statistic
import com.torczuk.github.io.blocking.infrastructure.db.CachedLastNSecondsNumberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/blocking/numbers")
class NumbersBlockingController(@Autowired val repository: CachedLastNSecondsNumberRepository) {

    private val logger = LoggerFactory.getLogger(NumbersBlockingController::class.java)

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun stats(): ResponseEntity<Statistic> {
        logger.info("get stats")
        val stats = repository.getAllLast()
        return ResponseEntity.ok(stats)
    }

    @RequestMapping(consumes = [APPLICATION_JSON_VALUE])
    fun add(@RequestBody number: Number): ResponseEntity<Void> {
        logger.info("post number {}", number)
        repository.add(number.value)
        return ResponseEntity.accepted().build()
    }
}
