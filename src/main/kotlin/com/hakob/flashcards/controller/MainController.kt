package com.hakob.flashcards.controller

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.Post
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.annotation.PostConstruct


@Controller
@RequestMapping("/index")
class MainController(
    val mainService: MainService,

    @Value("\${server.port}")
    var localPort: Int,
) {
    @GetMapping
    fun mainPage(model: Model): String {
        println("HeyPost: $localPort")
        model.addAttribute("post", Post())
        return "index"
    }

    @PostMapping
    fun save(post: Post, model: Model): String? {
        model.addAttribute("localPort", localPort)
        model.addAttribute("testVal", "myTestValue")

        val richTextWithoutLinks = mainService.hke(post.content)
        val newPost = Post("tt", richTextWithoutLinks)
        model.addAttribute("post", newPost)
        return "saved"
    }
}