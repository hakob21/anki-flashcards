package com.hakob.flashcards.service

import org.springframework.stereotype.Service

@Service
class WordService(
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
                forwardIterator.next()
            }
            if (backwardsIterator.hasPrevious() && backwardIsNotOver) {
                backwardIsNotOver = !backwardsIterator.sentenceIsOverOnNextPreviousWord<String>()
                if (backwardIsNotOver) {
                    backwardsIterator.previous()
                }
            }
        }

        return list.subList(backwardsIterator.nextIndex(), forwardIterator.nextIndex()).joinToString(separator = " ")
    }
}

fun <T> ListIterator<String>.sentenceIsOverOnNextForwardWord(): Boolean {
    println("Next: ${this.next()}")
    this.previous()

    val terminatorList = listOf(".", "!", "?")
    if (this.next().containsOneOfCharactersFromList(terminatorList)) {
        this.previous()
        return true
    }
    this.previous()
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

fun <T> ListIterator<String>.sentenceIsOverOnNextPreviousWord(): Boolean {
    println("Previous: ${this.previous()}")
    this.next()
    val terminatorList = listOf(".", "!", "?")
    if (this.previous().containsOneOfCharactersFromList(terminatorList)) {
        this.next()
        return true
    }
    this.next()
    return false
}
