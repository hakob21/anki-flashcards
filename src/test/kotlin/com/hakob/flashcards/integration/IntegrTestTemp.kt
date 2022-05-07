package com.hakob.flashcards.integration

import com.hakob.flashcards.SpringApp
import com.hakob.flashcards.service.MainService
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
    fun testMainMethodProcessingHtml() {
        // given
        val htmlText = javaClass.getResource("/com/hakob/flashcards/testHtmlFile.html").readText()
        val expectedTestHtmlFile = javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFile.html").readText()
        // when
        val result = mainService.hke(htmlText)

        // then
        result shouldBe expectedTestHtmlFile
    }

    @Test
    fun newtestMainMethodProcessingHtml() {
        // given
        val htmlText = javaClass.getResource("/com/hakob/flashcards/testHtmlFile2.html").readText()
//        val expectedTestHtmlFile = javaClass.getResource("/com/hakob/flashcards/resultTestHtmlFile.html").readText()

        // when
        val result = mainService.hke(htmlText)

        // then
//        result shouldBe expectedTestHtmlFile
        println(result)
    }


}