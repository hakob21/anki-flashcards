package com.hakob.flashcards.service

import com.hakob.flashcards.utils.FileUtils
import com.hakob.flashcards.utils.TranslateUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.safety.Whitelist
import org.springframework.stereotype.Service

@Service
class MainService(
    val translateUtils: TranslateUtils,
    val fileUtils: FileUtils
) {

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
            "img"
        )
        val listOfHtmlEntities = listOf(
            "&nbsp;",
            "&lt;",
            "&gt;",
        )
//        val only = Jsoup.clean(richText, Whitelist().addTags(*whiteListOfTags.toTypedArray()))
        val only = Jsoup.clean(richText, Whitelist.basicWithImages())


        val richTextWithoutLinks = Jsoup.clean(richText, Whitelist().addTags(*whiteListOfTags.toTypedArray()).removeTags("a"))
        val finalDoc = Jsoup.parse(richTextWithoutLinks)
        val paragraps = finalDoc.select("p")

        var i = 0
        paragraps.forEach {
            println(it.`val`())
            var sentence = ""
            for (word in it.text().split(" ")) {
                val trimmed =
                    """
                        <a name="word" href="#" onClick="return false;" id=${i++}>$word</a>
                    """.trimIndent().plus(" ")
                sentence += trimmed
            }
            println("Sentence: $sentence")
//            val first = it.childNodes().first().replaceWith(Element("a").attr("href", "https://google.com"))
            it.text(sentence)
        }

//        val richTextWithoutLinks = Jsoup.clean(richText, Whitelist())
        val images = finalDoc.select("img")
        images.forEach {
            it.attr("width", "500")
            it.attr("height", "500")
        }

        var fin = finalDoc.toString()
        fin = fin.replace("&lt;", "<")
        fin = fin.replace("&gt;", ">")
        println(fin)
        return fin
    }

    fun processTranslateRequest(wordIndex: Int, word: String, listOfWords: List<String>) {
        val translatedWord = translateUtils.getTranslatedWord(word)

        val sentence = listOfWords.joinToString(separator = " ")
        fileUtils.addWordToTxtFile(word, translatedWord, sentence)
    }

}