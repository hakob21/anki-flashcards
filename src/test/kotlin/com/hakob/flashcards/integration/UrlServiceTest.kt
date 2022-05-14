package com.hakob.flashcards.integration

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.WebClientOptions
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.hakob.flashcards.service.UrlService
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.OverrideMockKs
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlServiceTest {
    @MockK(relaxed = true)
    lateinit var webClient: WebClient
    @OverrideMockKs
    lateinit var urlService: UrlService

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true )
    }
    @Test
    fun `should process html`() {
        // given
        val url = "http://example.com"
        every { webClient.getPage<HtmlPage>(url).webResponse.contentAsString } returns javaClass.getResource("/com/hakob/flashcards/testHtmlFileDockerTutorial.html").readText()
        val expected = javaClass.getResource("/com/hakob/flashcards/testHtmlFileDockerTutorial.html").readText()

        // when
        val result = urlService.fetchHtmlPageAsString(url)

        // then
        result shouldBe expected
    }
}