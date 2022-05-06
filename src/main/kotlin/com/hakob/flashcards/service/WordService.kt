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

//        while (backwardIsNotOver || forwardIsNotOver) {
//            if (forwardIterator.hasNext() && forwardIsNotOver) {
//                forwardIsNotOver = !forwardIterator.sentenceIsOverOnNextForwardWord<String>()
//                forwardIterator.next()
//            }
//            if (backwardsIterator.hasPrevious() && backwardIsNotOver) {
//                backwardIsNotOver = !backwardsIterator.movePointerToPreviousIfSentenceIsNotOver<String>()
//                if (backwardIsNotOver) {
//                    backwardsIterator.previous()
//                }
//            }
//        }
        while (forwardIterator.movePointerToNextIfSentenceIsNotOver<String>()) {

        }
        while (backwardsIterator.movePointerToPreviousIfSentenceIsNotOver<String>()) {

        }
        return list.subList(backwardsIterator.nextIndex(), forwardIterator.nextIndex()).joinToString(separator = " ")
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

//    if (this.hasPrevious() && !this.previous().containsOneOfCharactersFromList(terminatorList)) {
//        return true
//    }
//    return false
}
