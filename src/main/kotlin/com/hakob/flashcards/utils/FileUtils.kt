package com.hakob.flashcards.utils

import org.springframework.stereotype.Service
import java.io.File

@Service
class FileUtils {
    fun addWordToTxtFile(word: String, translation: String, cardback: String) {
        println("Writing to file")
        val readyWord = if (isLetters(word)) {
            word
        } else {
            //if contains , . ! ? etc.
            word.substring(0, word.length - 1)
        }

        File("ankiFile.txt").appendText("\n$readyWord; $translation <br></br> $cardback")
    }

    private fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }
}