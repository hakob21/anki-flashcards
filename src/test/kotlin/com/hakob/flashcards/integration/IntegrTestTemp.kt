package com.hakob.flashcards.integration

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.TranslationRequest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrTestTemp(
    @LocalServerPort
    val port: Int,

    @Autowired
    val mainService: MainService
) {
    //TODO refactor this test after H1 H2 etc. are also hyperlinked
    @Test
    fun `should return correctly cleaned and processed html`() {
        // given
        val htmlText = javaClass.getResource("/com/hakob/flashcards/testHtmlFile.html").readText()
        val expectedStringHtml = javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFile.html").readText()
        // when
        val result: String = mainService.hke(htmlText)

        // then
        result shouldBe expectedStringHtml
    }

    @Test
    fun `should return correct entries (inputWord, translatedWord, context) for the flashcard`() {
        // given
        val htmlText = javaClass.getResource("/com/hakob/flashcards/testHtmlFile2.html").readText()
        val translationRequest: TranslationRequest = TranslationRequest(
            indexOfWordToTranslate = 199,
            wordToTranslate = "key",
            sentence = "This is a context sentence which contains the key word and the word 'key' here should be translated".split(" ")
        )
        val expected: Triple<String, String, String> = Triple("key", "ключ", "This is a context sentence which contains the key word and the word 'key' here should be translated")

        // when
        val result: String = mainService.hke(htmlText)

        // then
        println(result)

        // when
        val result2 = mainService.processTranslateRequest(translationRequest.indexOfWordToTranslate, translationRequest.wordToTranslate, translationRequest.sentence)

        // then
        result2 shouldBe expected

    }

//    @Test
//    fun newtestMainMethodProcessingHtml() {
//        // given
//        val htmlText = javaClass.getResource("/com/hakob/flashcards/testHtmlFile2.html").readText()
////        val expectedTestHtmlFile = javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFile.html").readText()
//
//        // when
//        val result = mainService.hke(htmlText)
//
//        // then
////        result shouldBe expectedTestHtmlFile
//        println(result)
//    }


}