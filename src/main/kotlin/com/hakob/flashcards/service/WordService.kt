package com.hakob.flashcards.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import org.springframework.stereotype.Service

val terminatorList = listOf(".", "!", "?")

@Service
class WordService(
    var listOfWords: List<String> = listOf(),
    var document: Document = Document("")
) {
    fun getTargetParagraphOrFallbackToSentenceInParagraph(wordId: Int): String {
        val paragraphs: Elements = document.select("p")

        //TODO: what to do in case of paragraph not found? Throw exception?
        val paragraph: Node? = paragraphs.select("a").find { it.id().equals(wordId.toString()) }?.parentNode()

        val paragraphElement = paragraph as Element
        val cleanParagraphString = Jsoup.clean(paragraphElement.html(), Whitelist.none())

        if (cleanParagraphString.containsOneOfCharactersFromList(terminatorList)) {
            return getTargetSentence(wordId, cleanParagraphString.split(" "))
        }
        return cleanParagraphString
    }

    fun getTargetSentence(wordId: Int, listOfWordsInParagraph: List<String> = listOfWords): String {
        val forwardIterator = listOfWordsInParagraph.listIterator(wordId)
        val backwardsIterator = listOfWordsInParagraph.listIterator(wordId)

        while (forwardIterator.movePointerToNextIfSentenceIsNotOver<String>());

        while (backwardsIterator.movePointerToPreviousIfSentenceIsNotOver<String>());

        return listOfWordsInParagraph.subList(backwardsIterator.nextIndex(), forwardIterator.nextIndex()).joinToString(separator = " ")
    }
}

fun <T> ListIterator<String>.movePointerToNextIfSentenceIsNotOver(): Boolean {
    if (this.hasNext() && !this.next().containsOneOfCharactersFromList(terminatorList) ) {
        return true
    }
    return false
}

fun String.containsOneOfCharactersFromList(terminatorList: List<String>): Boolean {
    terminatorList.forEach {
        if (this.contains(it)) {
            return true
        }
    }
    return false
}

fun <T> ListIterator<String>.movePointerToPreviousIfSentenceIsNotOver(): Boolean {
    return if (this.hasPrevious()) {
        if (this.previous().containsOneOfCharactersFromList(terminatorList)) {
            this.next()
            false
        } else {
            true
        }
    } else {
        false
    }
}
