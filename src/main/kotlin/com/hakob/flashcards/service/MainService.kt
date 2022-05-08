package com.hakob.flashcards.service

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
    var whiteListOfTags: List<String> =
        listOf("h1", "h2", "h3", "h4", "h5", "h6", "p", "time", "div", "ul", "li", "code", "img")

    fun hke(richText: String): String {
        val richTextWithoutLinks: String =
            Jsoup.clean(richText, Whitelist().addTags(*whiteListOfTags.toTypedArray()).removeTags("a"))
        val jsoupDocument: Document = Jsoup.parse(richTextWithoutLinks)
        val paragraphs: Elements = jsoupDocument.select("p")

        // var to pass to wordservice
        val listOfWordsFormParagraphs = mutableListOf<String>()

        var i = 0
        paragraphs.forEach { paragraph ->
            var sentence = ""
            val listOfWordsFromParagraph: List<String> = paragraph.text().split(" ")
            for (word in listOfWordsFromParagraph) {
                val wordToken =
                    """
                        <a name="word" href="#" onClick="return false;" id=${i++}>$word</a>
                    """.trimIndent().plus(" ")
                sentence += wordToken
                listOfWordsFormParagraphs.add(word)
            }
            paragraph.text(sentence)
        }

        wordService.listOfWords = listOfWordsFormParagraphs
        normaliseImageSizes(jsoupDocument)

        var stringHtml = getStringHtmlWithReplacedHtmlEntitiesWithCharacters(jsoupDocument)
        stringHtml = trimIndentations(stringHtml)

        wordService.document = Jsoup.parse(stringHtml)

        return stringHtml
    }

    private fun normaliseImageSizes(jsoupDocument: Document) {
        val images = jsoupDocument.select("img")
        images.forEach {
            it.attr("width", "500")
            it.attr("height", "500")
        }
    }

    private fun trimIndentations(stringHtml: String): String {
        var fin1 = stringHtml
        fin1 = fin1.lines().joinToString("\n") {
            it.trim()
        }
        return fin1
    }

    private fun getStringHtmlWithReplacedHtmlEntitiesWithCharacters(finalDoc: Document): String {
        return finalDoc.toString()
            .replace("&lt;", "<")
            .replace("&gt;", ">")
    }

    fun processTranslateRequest(wordIndex: Int, word: String, listOfWords: List<String>): Triple<String, String, String> {
        val translatedWord = translateUtils.getTranslatedWord(word)

//        val sentence = listOfWords.joinToString(separator = " ")
        val sentence = wordService.getTargetParagraphOrFallbackToSentenceInParagraph(wordIndex)
        fileUtils.addWordToTxtFile(word, translatedWord, sentence)

        return Triple(word, translatedWord, sentence)
    }

}