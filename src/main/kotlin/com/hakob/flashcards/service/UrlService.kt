package com.hakob.flashcards.service

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.springframework.stereotype.Service

@Service
class UrlService(
    val webClient: WebClient = WebClient()
) {
    fun fetchHtmlPageAsString(url: String): String {
        webClient.options.isThrowExceptionOnScriptError = false
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isCssEnabled = true

        webClient.options.isJavaScriptEnabled = true
        val ll = webClient.getPage<HtmlPage>(url)
        ll.asXml()
        return ll.webResponse.contentAsString
    }
}