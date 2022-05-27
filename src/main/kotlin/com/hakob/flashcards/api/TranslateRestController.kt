package com.hakob.flashcards.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.hakob.flashcards.model.TranslationSuccessMessage
import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.TranslationRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translate")
class TranslateRestController(
    val mainService: MainService,
) {
    @PostMapping(consumes = arrayOf("application/json"), produces = arrayOf("application/json"))
    fun toTranslate(@RequestBody translationRequest: TranslationRequest): String {
        val result = mainService.processTranslateRequest(translationRequest.indexOfWordToTranslate, translationRequest.wordToTranslate)
        return TranslationSuccessMessage(
            word = result.first,
            translatedWord = result.second,
            sentence = result.third
        ).toString()
    }
}