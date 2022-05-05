package com.hakob.flashcards.controller

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.Post
import com.hakob.flashcards.service.TranslationRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translate")
class RestController(
    val mainService: MainService
) {
    @GetMapping
    fun mainPage(model: Model): String {
        model.addAttribute("post", Post())
        return "index"
    }

    @PostMapping
    fun toTranslate(translationRequest: TranslationRequest, model: Model): String? {
        println("TranslationRequest  $translationRequest")
        return "saved"
    }
}