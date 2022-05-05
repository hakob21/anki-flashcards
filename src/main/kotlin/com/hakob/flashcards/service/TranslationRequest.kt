package com.hakob.flashcards.service

data class TranslationRequest(
    val indexOfWordToTranslate: Int,
    val wordToTranslate: String,
    val sentence: List<String>
) {
}