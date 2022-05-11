package com.hakob.flashcards.utils

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
}