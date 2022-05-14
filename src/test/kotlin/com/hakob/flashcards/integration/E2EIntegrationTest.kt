package com.hakob.flashcards.integration

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.hakob.flashcards.frontendController.FrontendController
import com.hakob.flashcards.api.TranslateRestController
import com.hakob.flashcards.frontendController.UrlProviderController
import com.hakob.flashcards.utils.FileUtils
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class E2EIntegrationTest(
    @LocalServerPort
    var port: Int,

    @Autowired
    val translateRestController: TranslateRestController,

    @Autowired
    val frontendController: FrontendController,

    @Autowired
    val fileUtils: FileUtils,

    @Autowired
    val urlProviderController: UrlProviderController

    ) {
    lateinit var webClient: WebClient
    lateinit var testAnkiFile: File

    @BeforeEach
    fun initialize() {
        webClient = WebClient()
        frontendController.localPort = port
        urlProviderController.localPort = port
        val filePathInResources = javaClass.getResource(".").file + "testAnkiFile.txt"
        fileUtils.setFilePathInResources(filePathInResources)
        testAnkiFile = File(filePathInResources)
        // if for any reason file was not deleted after previous test execution
        testAnkiFile.delete()

        webClient.options.isThrowExceptionOnScriptError = false
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isCssEnabled = true

        webClient.options.isJavaScriptEnabled = true
    }

    @AfterEach
    fun tearDown() {
        webClient.close()
        testAnkiFile.delete()
    }

    @Test
    fun `should test one translation flow E2E from`() {
        // given
        val url = "http://localhost:$port/index"
        val idOfWordToTranslate = "199"


        val expected = "key; ключ <br></br> This is a context sentence which contains the key word and the word 'key' here should be translated"

        // when
        val indexPage = webClient.getPage<HtmlPage>(url)
        val textAreaOnIndexPage = indexPage.getElementById("content")
        textAreaOnIndexPage.textContent = javaClass.getResource("/com/hakob/flashcards/testHtmlFile2.html").readText()
        val generatedHtmlPage = indexPage.getElementById("ta-submit").click<HtmlPage>()

        val wordToTranslateFromGeneratedHtmlPage = generatedHtmlPage.getElementById(idOfWordToTranslate)
        wordToTranslateFromGeneratedHtmlPage.click<HtmlPage>()

        // in order for javascript eventListener function to have time to execute
        Thread.sleep(1000)

        // expected
        val textFromTestAnkiFile = testAnkiFile.readText()
        textFromTestAnkiFile.lines() shouldHaveSize 1
        textFromTestAnkiFile.lines().first() shouldBe expected
    }
}