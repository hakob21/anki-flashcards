package com.hakob.flashcards.model

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class TranslationSuccessMessage(
    val word: String,
    val translatedWord: String,
    val sentence: String
) {
    override fun toString(): String {
        return """
            <p><strong>word:</strong> $word,</p>
            <p><strong>ranslatedWord:</strong> $translatedWord,</p>
            <p><strong>sentence:</strong> $sentence</p>
        """.trimIndent()
    }
}
