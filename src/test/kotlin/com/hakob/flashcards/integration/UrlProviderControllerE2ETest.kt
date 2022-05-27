package com.hakob.flashcards.integration

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.hakob.flashcards.frontendController.UrlProviderController
import com.hakob.flashcards.service.trimIndentations
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlProviderControllerE2ETest(
    @LocalServerPort
    var port: Int,

    @Autowired
    val urlProviderController: UrlProviderController
) {
    lateinit var webClient: WebClient


    @BeforeAll
    fun init() {
        webClient = WebClient()
        urlProviderController.localPort = port

        webClient.options.isThrowExceptionOnScriptError = false
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isCssEnabled = true

        webClient.options.isJavaScriptEnabled = true
    }

    @AfterAll
    fun tearDown() {
        webClient.close()
    }

    @Test
    fun `should test url provide flow E2E`() {
        // given
        val url = "http://localhost:$port/url"
        val testUrl = "https://www.educative.io/blog/docker-compose-tutorial"
        val expected = javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFileDockerTutorial.html").readText().trimIndentations()


        // when
        val provideLinkPage: HtmlPage = webClient.getPage<HtmlPage>(url)
        val form = provideLinkPage.getFormByName("urlForm")
        val urlInput = form.getInputByName<HtmlInput>("htmlPageUrl")
        urlInput.valueAttribute = testUrl
        val submitButton = provideLinkPage.getElementById("submitButton")
        val resultPage = submitButton.click<HtmlPage>()

        // then

        var ankiFile = File("testResultFileDel.html")
        ankiFile.delete()

        ankiFile.createNewFile()
        ankiFile.writeText(resultPage.webResponse.contentAsString.trimIndentations())

        resultPage.webResponse.contentAsString.trimIndentations() shouldContainIgnoringCase """
            <script type="text/javascript">var port = $port</script>
            <script type="text/javascript" src="/js/onClickActions.js"></script>
        """.trimIndent().trimIndentations()

        println("DONEEEEEEE")

//        resultPage.webResponse.contentAsString.trimIndentations() shouldBe expected


    }
}