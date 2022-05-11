package com.hakob.flashcards.integration

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.hakob.flashcards.frontendController.MainController
import com.hakob.flashcards.api.RestController
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
class HtmlUnitIntegrationTest(
    @LocalServerPort
    var port: Int,

    @Autowired
    val restController: RestController,

    @Autowired
    val mainController: MainController,

    @Autowired
    val fileUtils: FileUtils
) {
    lateinit var webClient: WebClient
    lateinit var testAnkiFile: File

    @BeforeEach
    fun initialize() {
        webClient = WebClient()
        mainController.localPort = port
        val filePathInResources = javaClass.getResource(".").file + "testAnkiFile.txt"
        fileUtils.setFilePathInResources(filePathInResources)
        testAnkiFile = File(filePathInResources)
        // if for any reason file was not deleted after previous test execution
        testAnkiFile.delete()
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

        webClient.options.isThrowExceptionOnScriptError = false
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isCssEnabled = true

        webClient.options.isJavaScriptEnabled = true

        val expected = "key; ключ <br></br> This is a context sentence which contains the key word and the word 'key' here should be translated"

        // when
        val indexPage = webClient.getPage<HtmlPage>(url)
        println("xhke")
        println("xhke")
        val textAreaOnIndexPage = indexPage.getElementById("content")
        textAreaOnIndexPage.textContent = javaClass.getResource("/com/hakob/flashcards/testHtmlFile2.html").readText()
        val generatedHtmlPage = indexPage.getElementById("ta-submit").click<HtmlPage>()

        println(generatedHtmlPage.webResponse.contentAsString)
        val wordToTranslateFromGeneratedHtmlPage = generatedHtmlPage.getElementById(idOfWordToTranslate)
        println(wordToTranslateFromGeneratedHtmlPage.textContent)
        wordToTranslateFromGeneratedHtmlPage.click<HtmlPage>()

        // in order for javascript eventListener function to have time to execute
        Thread.sleep(1000)

        // expected
        val textFromTestAnkiFile = testAnkiFile.readText()
        println("First: ${ testAnkiFile.readText().lines().first() }")
        textFromTestAnkiFile.lines() shouldHaveSize 1
        textFromTestAnkiFile.lines().first() shouldBe expected
    }
}