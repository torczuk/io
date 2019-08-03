package com.torczuk.github.io.blocking.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@RestController
@RequestMapping("/api/v1/non-blocking/files")
class FilesNonBlockingController(@Value("\${application.files}") val files: String) {

    val logger = LoggerFactory.getLogger(FilesNonBlockingController::class.java)

    @GetMapping
    (produces = [MediaType.APPLICATION_PDF_VALUE])
    fun download(): Flux<DataBuffer> {
        val callable = {
            Files.newInputStream(Paths.get("$files/idea.pdf"), StandardOpenOption.READ)
        }
        val factory = DefaultDataBufferFactory()
        return DataBufferUtils.readInputStream(callable, factory, 100 * 1024)
    }
}