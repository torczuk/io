package com.torczuk.github.io.blocking.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/api/v1/blocking/files")
class FileBlockingController(@Value("\${application.files}") val files: String) {

    private val logger = LoggerFactory.getLogger(FileBlockingController::class.java)

    @GetMapping(produces = [MediaType.APPLICATION_PDF_VALUE])
    fun download(): ResponseEntity<ByteArray> {
        logger.info("downloading file")
        return ResponseEntity.ok(Files.readAllBytes(Paths.get("$files/idea.pdf")))
    }
}