package com.hakob.flashcards.utils

import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.util.*

@Service
class FileUtils {
    var ankiFile = File("ankiFile.txt")

    fun addWordToTxtFile(word: String, translation: String, cardback: String) {
        println("Writing to file")
        val readyWord = if (isLetters(word)) {
            word
        } else {
            //if contains , . ! ? etc.
            word.substring(0, word.length - 1)
        }

        if (ankiFile.exists()) {
            println("Hke file exists")
            ankiFile.appendText("\n$readyWord; $translation <br></br> $cardback")
        } else {
            println("Hke file created")
            ankiFile.createNewFile()
            ankiFile.writeText("$readyWord; $translation <br></br> $cardback")
            rewriteLineToFileBeginning()
        }
    }

    private fun rewriteLineToFileBeginning() {
        val scanner = Scanner(ankiFile)
        val nextLine = scanner.nextLine()
        scanner.close()

        val fileWriter = FileWriter(ankiFile)
        fileWriter.write(nextLine)
        fileWriter.close()
    }

    fun setFilePathInResources(filePathInResources: String) {
        ankiFile = File(filePathInResources)
    }

    private fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }
}