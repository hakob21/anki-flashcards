//package com.hakob.flashcards
//
//import com.hakob.flashcards.service.MainService
//import com.hakob.flashcards.utils.TranslateUtils
//import io.kotest.matchers.shouldBe
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import java.io.File
//
//
//class MainServiceTest {
//    val translateUtils: TranslateUtils =
//    val mainService: MainService = MainService(
//        translateUtils = ,
//        fileUtils = ,
//        wordService =
//    )
//
////    @Test
////    fun hkeTest() {
////        val file = File("src/test/kotlin/resources/testHtmlFile.html")
////
////
//////        File(javaClass.getResource("src/test/kotlin/resources/testHtmlFile.html").toURI()).exists() shouldBe true
////        val classLoader = this.javaClass.classLoader
////        val fileCl = File(classLoader.getResource("testHtmlFile.html").file)
//////        val fileCl = File(classLoader.getResource("application.properties").file)
//////
////        fileCl.exists() shouldBe true
////
//////        val url = this.javaClass.getResource("/testHtmlFile.html")
//////        val fileClasRes = File(this.javaClass.getResource("/testHtmlFile.html").file)
//////        fileClasRes.exists() shouldBe true
////    }
//
//    @Test
//    fun hkeTest2() {
//        // given
//        val url = javaClass.getResource("/com/hakob/flashcards/testHtmlFile.html")
//        val htmlRichText = url.readText()
//
//        // when
//        mainService.hke(htmlRichText)
//
//
//        // then
//
//    }
//}