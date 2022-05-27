package com.hakob.flashcards.frontendController

import com.hakob.flashcards.api.UrlRestController
import com.hakob.flashcards.service.SubmitFormTh
import com.hakob.flashcards.service.TemplateService
import com.hakob.flashcards.service.trimIndentations
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.jsoup.parser.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.io.File

@Controller
class UrlProviderController(
    val urlRestController: UrlRestController,

    @Value("\${server.port}")
    var localPort: Int,

    val templateService: TemplateService
) {

    var i: Int = 1
    @GetMapping("/url")
    fun provideUrlPage(): String {
        return "provideLink"
    }

    @PostMapping("/url")
//    fun provideUrl(@ModelAttribute(value = "homepage") url: String, model: Model): String {
    fun provideUrl(@RequestParam("htmlPageUrl") url: String, model: Model): String {
        model.addAttribute("localPort", localPort)
        model.addAttribute("testVal", "myTestValue")

        var richTextWithoutLinks = urlRestController.processUrl(url)
//            mb delete
            .trimIndentations()
//        val newSubmitFormTh = SubmitFormTh("tt", richTextWithoutLinks)
//        model.addAttribute("submitForm", newSubmitFormTh)

        val jsoup = Jsoup.parse(richTextWithoutLinks)
        val attributes = Attributes()
        attributes.put(
            Attribute(
                "type",
                "text/javascript"
            )
        )
        attributes.put(
            Attribute(
                "th:inline",
                "javascript"
            )
        )

        jsoup.body()
            .appendChild(
                Element(
                    Tag.valueOf("script"),
                    "",
                    attributes
                ).text(
//                    "var port = [[${'$'}{localPort}]]"
                    "var port = [[${localPort}]]"
                )
            )

        val attributes2 = Attributes()
        attributes2.put(
            Attribute(
                "type",
                "text/javascript"
            )
        )
        attributes2.put(
            Attribute(
                "th:src",
                "@{/js/onClickActions.js}"
            )
        )
        jsoup.body()
            .appendChild(
                Element(
                    Tag.valueOf("script"),
                    "",
                    attributes2
                )
            )

//            .appendText(
//                """
//<script type="text/javascript" th:inline="javascript">
//    var port = [[${'$'}{localPort}]]
//</script>
//
//<script type="text/javascript" th:src="@{/js/onClickActions.js}"></script>
//            """.trimIndent()
//            )


//            .appendChild(
//            Element(
//                Tag.valueOf("script"),
//                "",
//                Attribute(
//                    "type",
//                    "text/javascript"
//                )
//            ).text(
//            )
//        )


//        val finalRichText = richTextWithoutLinks.plus(
//            """
//<script type="text/javascript" th:inline="javascript">
//    var port = [[${'$'}{localPort}]]
//</script>
//
//<script type="text/javascript" th:src="@{/js/onClickActions.js}"></script>
//            """.trimIndent()
//        )

        val finalRichText = jsoup.html()
            // mb delete
            .trimIndentations()

        return templateService.createNewTepmlateAndReturn(finalRichText)
//        if (i == 1) {
//
//            i++
//            val finalRichText = jsoup.html()
//                // mb delete
//                .trimIndentations()
//
//            return templateService.createNewTepmlateAndReturn(finalRichText)
//            val filePathInResources = javaClass.getResource("/templates").file + "/newSaved.html"
//            val f = File(filePathInResources)
//
//            if (f.exists()) {
//                f.delete()
//            }
//            f.createNewFile()
//            f.writeText(finalRichText)
//
//            return "newSaved"
//        } else {
//
//            val finalRichText = jsoup.html()
//                // mb delete
//                .trimIndentations()
//
//            val filePathInResources = javaClass.getResource("/templates").file + "/newSaved2.html"
//            val f = File(filePathInResources)
//
//            if (f.exists()) {
//                f.delete()
//            }
//            f.createNewFile()
//            f.writeText(finalRichText)
//
//            return "newSaved2"
//        }
    }
}