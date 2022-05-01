package com.hakob.flashcards.testPack

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.springframework.stereotype.Service

@Service
class TranslateUtils {
    val translate: Translate = TranslateOptions.getDefaultInstance().toBuilder().setTargetLanguage("ru").build().service

    fun getTranslatedWord(word: String): String {
        val translation = translate.translate(word)
        System.out.printf("Translated Text:\n\t%s\n", translation.translatedText)

        return translation.translatedText
    }

    fun getWordsFromSentence(indexOfWord: Int, listOfWordsFromText: List<String>): List<String> {
        var endIndex: Int = 0;
        var startIndex: Int = 0;

        for (i in indexOfWord until listOfWordsFromText.size) {
            if (listOfWordsFromText.get(i).contains(".") || i + 1 == listOfWordsFromText.size) {
                endIndex = i;
                break
            }
        }

        for (i in indexOfWord downTo 0) {
            if (i == indexOfWord && listOfWordsFromText.get(i).contains(".")) {
                continue
            }
            if (listOfWordsFromText.get(i).contains(".")) {
                startIndex = i + 1;
                break
            }
            if (i == 0) {
                startIndex = 0
                break
            }
        }

        println("startIndex $startIndex")
        println("endIndex $endIndex")

        val sentence: List<String> = listOfWordsFromText.slice(startIndex..endIndex)

        println("Sentence: $sentence")

        return sentence
    }

}