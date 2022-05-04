package com.hakob.flashcards.controller

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.Post
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/index")
class MainController(
    val mainService: MainService
) {
    @GetMapping
    fun mainPage(model: Model): String {
        model.addAttribute("post", Post())
        return "index"
    }

    @PostMapping
    fun save(post: Post?, model: Model): String? {
        model.addAttribute("post", post)
        return "saved"
    }
}