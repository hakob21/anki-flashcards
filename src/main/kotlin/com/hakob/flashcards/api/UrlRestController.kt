package com.hakob.flashcards.api

import com.hakob.flashcards.main
import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.UrlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlRestController(
    val urlService: UrlService,
    val mainService: MainService
) {

    @PostMapping("/url", produces = ["application/json"])
    fun processUrl(@RequestBody url: String): String {
        val htmlPageAsString = urlService.fetchHtmlPageAsString(url)
        return mainService.submitPageAndReturnGeneratedPage(htmlPageAsString)
    }
}