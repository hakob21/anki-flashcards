package com.hakob.flashcards.api

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.TranslationRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translate")
class RestController(
    val mainService: MainService
) {
    @PostMapping(consumes = arrayOf("application/json"), produces = arrayOf("application/json"))
    fun toTranslate(@RequestBody translationRequest: TranslationRequest): TranslationRequest {
        mainService.processTranslateRequest(translationRequest.indexOfWordToTranslate, translationRequest.wordToTranslate)
        return translationRequest
    }

    @PostMapping
    fun runProcess(@RequestBody wordId: Int) {

    }
}