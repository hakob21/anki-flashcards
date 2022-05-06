package com.hakob.flashcards.service

import org.springframework.stereotype.Service

@Service
class WordService(
//    var text: String = "version control logs, should. show that test code is checked. in each time product code",
    var map: MutableMap<Int, String> = mutableMapOf()
) {
    fun getTargetSentence(wordId: Int): String {
        val list: List<String> = map.values.toList()
        val forwardIterator = list.listIterator(wordId)
        val backwardsIterator = list.listIterator(wordId)

        var backwardIsNotOver = true
        var forwardIsNotOver = true

        while (backwardIsNotOver || forwardIsNotOver) {
            if (forwardIterator.hasNext() && forwardIsNotOver) {
                forwardIsNotOver = !forwardIterator.sentenceIsOverOnNextForwardWord<String>()
            }
            if (backwardsIterator.hasPrevious() && backwardIsNotOver) {
                backwardIsNotOver = !backwardsIterator.sentenceIsOverOnNextPreviousWord<String>()
            }
        }

        return list.subList(backwardsIterator.nextIndex(), forwardIterator.nextIndex()).joinToString(separator = " ")
    }
}

private fun <T> ListIterator<String>.sentenceIsOverOnNextForwardWord(): Boolean {
    val terminatorList = listOf(".", "!", "?")
    if (this.next().containsOneOfCharactersFromList(terminatorList)) {
        return true
    }
    return false
}

private fun String.containsOneOfCharactersFromList(terminatorList: List<String>): Boolean {
    terminatorList.forEach {
        if (this.contains(it)) {
            return true
        }
    }
    return false
}

private fun <T> ListIterator<String>.sentenceIsOverOnNextPreviousWord(): Boolean {
    val terminatorList = listOf(".", "!", "?")
    if (this.previous().containsOneOfCharactersFromList(terminatorList)) {
        this.next()
        return true
    }
    return false
}
