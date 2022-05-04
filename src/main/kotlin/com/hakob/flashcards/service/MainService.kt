package com.hakob.flashcards.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.safety.Whitelist
import org.springframework.stereotype.Service

@Service
class MainService {

    fun hke(richText: String): String {
        val document = Jsoup.parse(richText)
        val onlyTextFromHtmlA = Jsoup.clean(richText, Whitelist.none())


        val whiteListOfTags = listOf(
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "p",
            "time",
            "div",
            "ul",
            "li",
            "code",
        )
        val listOfHtmlEntities = listOf(
            "&nbsp;",
            "&lt;",
            "&gt;",
        )
//        val only = Jsoup.clean(richText, Whitelist().addTags(*whiteListOfTags.toTypedArray()))
        val only = Jsoup.clean(richText, Whitelist.basicWithImages())


        val richTextWithoutLinks = Jsoup.clean(richText, Whitelist.basicWithImages().removeTags("a"))
        val finalDoc = Jsoup.parse(richTextWithoutLinks)
        val paragraps = finalDoc.select("p")

        paragraps.forEach {
            println(it.`val`())
            var sentence = ""
            for (word in it.text().split(" ")) {
                val trimmed =
                    """
                        <a href="http://google.com">$word</a>
                    """.trimIndent().plus(" ")
                sentence += trimmed
            }
            println("Sentence: $sentence")
//            val first = it.childNodes().first().replaceWith(Element("a").attr("href", "https://google.com"))
            it.text(sentence)
        }

//        val richTextWithoutLinks = Jsoup.clean(richText, Whitelist())
        var fin = finalDoc.toString()
        fin = fin.replace("&lt;", "<")
        fin = fin.replace("&gt;", ">")
        return fin
    }

}