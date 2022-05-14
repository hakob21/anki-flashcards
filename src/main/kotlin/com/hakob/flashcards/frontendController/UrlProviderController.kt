package com.hakob.flashcards.frontendController

import com.hakob.flashcards.api.UrlRestController
import com.hakob.flashcards.service.SubmitFormTh
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UrlProviderController(
    val urlRestController: UrlRestController,

    @Value("\${server.port}")
    var localPort: Int,
) {

    @GetMapping("/url")
    fun provideUrlPage(): String {
        return "provideLink"
    }

    @PostMapping("/url")
//    fun provideUrl(@ModelAttribute(value = "homepage") url: String, model: Model): String {
    fun provideUrl(@RequestParam("htmlPageUrl") url: String, model: Model): String {
        model.addAttribute("localPort", localPort)
        model.addAttribute("testVal", "myTestValue")

        val richTextWithoutLinks = urlRestController.processUrl(url)
        val newSubmitFormTh = SubmitFormTh("tt", richTextWithoutLinks)
        model.addAttribute("submitForm", newSubmitFormTh)

        return "saved"
    }
}