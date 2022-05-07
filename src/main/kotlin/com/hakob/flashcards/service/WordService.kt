package com.hakob.flashcards.service

import org.springframework.stereotype.Service

@Service
class WordService(
    var listOfWords: List<String> = listOf()
) {
    fun getTargetSentence(wordId: Int): String {
        val forwardIterator = listOfWords.listIterator(wordId)
        val backwardsIterator = listOfWords.listIterator(wordId)

        while (forwardIterator.movePointerToNextIfSentenceIsNotOver<String>());

        while (backwardsIterator.movePointerToPreviousIfSentenceIsNotOver<String>());

        return listOfWords.subList(backwardsIterator.nextIndex(), forwardIterator.nextIndex()).joinToString(separator = " ")
    }
}

fun <T> ListIterator<String>.movePointerToNextIfSentenceIsNotOver(): Boolean {
    val terminatorList = listOf(".", "!", "?")
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
    val terminatorList = listOf(".", "!", "?")
    if (this.hasPrevious()) {
        if (this.previous().containsOneOfCharactersFromList(terminatorList)) {
            this.next()
            return false
        } else {
            return true
        }
    } else {
        return false
    }
}
