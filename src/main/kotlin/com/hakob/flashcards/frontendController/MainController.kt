package com.hakob.flashcards.frontendController

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.SubmitFormTh
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


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
        model.addAttribute("submitForm", SubmitFormTh())
        return "index"
    }

    @PostMapping
    fun save(submitFormTh: SubmitFormTh, model: Model): String? {
        model.addAttribute("localPort", localPort)
        model.addAttribute("testVal", "myTestValue")

        val richTextWithoutLinks = mainService.submitPageAndReturnGeneratedPage(submitFormTh.content)
        val newSubmitFormTh = SubmitFormTh("tt", richTextWithoutLinks)
        model.addAttribute("submitForm", newSubmitFormTh)
        return "saved"
    }
}