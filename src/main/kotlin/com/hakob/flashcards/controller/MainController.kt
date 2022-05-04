package com.hakob.flashcards.controller

import com.google.cloud.translate.Translate.TranslateOption.model
import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.Post
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MainController(
    val mainService: MainService
) {
    @GetMapping("/index")
    fun mainPage(model: Model): String {
        model.addAttribute("post", Post())
        return "index"
    }
}