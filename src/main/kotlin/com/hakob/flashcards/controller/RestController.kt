package com.hakob.flashcards.controller

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
    fun toTranslate(@RequestBody translationRequest: TranslationRequest) {
        println("TranslationRequest  $translationRequest")
        mainService.processTranslateRequest(translationRequest.indexOfWordToTranslate, translationRequest.wordToTranslate, translationRequest.sentence)
    }
}