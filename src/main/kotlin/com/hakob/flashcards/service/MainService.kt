package com.hakob.flashcards.service

import com.google.api.client.util.Value
import com.hakob.flashcards.utils.FileUtils
import com.hakob.flashcards.utils.TranslateUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import org.springframework.stereotype.Service

@Service
class MainService(
    val translateUtils: TranslateUtils,
    val fileUtils: FileUtils,
    val wordService: WordService

) {
//    @Value("whiteListOfTags")
    var whiteListOfTags: List<String> = listOf("h1","h2","h3","h4","h5","h6","p","time","div","ul","li","code","img")

    fun hke(richText: String): String {
        val richTextWithoutLinks: String = Jsoup.clean(richText, Whitelist().addTags(*whiteListOfTags.toTypedArray()).removeTags("a"))
        val finalDoc: Document = Jsoup.parse(richTextWithoutLinks)
        val paragraps: Elements = finalDoc.select("p")

        // var to pass to wordservice
        val list = mutableListOf<String>()

        var i = 0
        paragraps.forEach {
            paragraph ->
            println(paragraph.`val`())
            var sentence = ""
            val wordsFromParagraph: List<String> = paragraph.text().split(" ")
            for (word in wordsFromParagraph) {
                val trimmed =
                    """
                        <a name="word" href="#" onClick="return false;" id=${i}>$word</a>
                    """.trimIndent().plus(" ")
                sentence += trimmed
                list.add(word)
            }
            println("Sentence: $sentence")
//            val first = it.childNodes().first().replaceWith(Element("a").attr("href", "https://google.com"))
            paragraph.text(sentence)
        }

        wordService.list = list
//        val richTextWithoutLinks = Jsoup.clean(richText, Whitelist())
        val images = finalDoc.select("img")
        images.forEach {
            it.attr("width", "500")
            it.attr("height", "500")
        }

        var fin = replaceHtmlEntitiesWithCharacters(finalDoc)
        fin = fin.split("\n").map {
            it.trimEnd()
        }.joinToString("\n")

        println(fin)
        return fin
    }

    private fun replaceHtmlEntitiesWithCharacters(finalDoc: Document): String {
        var fin = finalDoc.toString()
        fin = fin.replace("&lt;", "<")
        fin = fin.replace("&gt;", ">")
        return fin
    }

    fun processTranslateRequest(wordIndex: Int, word: String, listOfWords: List<String>) {
        val translatedWord = translateUtils.getTranslatedWord(word)

        val sentence = listOfWords.joinToString(separator = " ")
        fileUtils.addWordToTxtFile(word, translatedWord, sentence)
    }

}