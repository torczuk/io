package com.torczuk.github.io.blocking.infrastructure.http

import com.torczuk.github.io.blocking.domain.Statistic
import com.torczuk.github.io.blocking.infrastructure.db.CachedLastNSecondsNumberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("api/v1/non-blocking/numbers")
class NumbersNonBlockingController(@Autowired val repository: CachedLastNSecondsNumberRepository) {

    private val logger = LoggerFactory.getLogger(NumbersNonBlockingController::class.java)

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun stats(): ResponseEntity<Mono<Statistic>> {
        logger.info("get stats")
        return ResponseEntity.ok(repository.getAllLast())
    }

    @RequestMapping(consumes = [APPLICATION_JSON_VALUE])
    fun add(@RequestBody number: Number): ResponseEntity<Mono<Boolean>> {
        logger.info("post number {}", number)
        val add = repository.add(number.value).subscribeOn(Schedulers.elastic())
        return ResponseEntity.accepted().body(add)
    }
}
