package com.hakob.flashcards

import com.hakob.flashcards.service.MainService
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.io.File


class MainServiceTest {
    @Autowired
    lateinit var mainService: MainService

//    @Test
//    fun hkeTest() {
//        val file = File("src/test/kotlin/resources/testHtmlFile.html")
//
//
////        File(javaClass.getResource("src/test/kotlin/resources/testHtmlFile.html").toURI()).exists() shouldBe true
//        val classLoader = this.javaClass.classLoader
//        val fileCl = File(classLoader.getResource("testHtmlFile.html").file)
////        val fileCl = File(classLoader.getResource("application.properties").file)
////
//        fileCl.exists() shouldBe true
//
////        val url = this.javaClass.getResource("/testHtmlFile.html")
////        val fileClasRes = File(this.javaClass.getResource("/testHtmlFile.html").file)
////        fileClasRes.exists() shouldBe true
//    }

    @Test
    fun hkeTest2() {
        println(javaClass.getResource("/com/hakob/flashcards/testHtmlFile.html").file)
//        println(javaClass.getResource("/com/hakob/flashcards/stylesheet.css").file)
//        val url = File(javaClass.getResource("testHtmlFile.html").file)
//        url.exists() shouldBe true
    }
}