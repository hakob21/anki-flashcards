package com.hakob.flashcards

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Hyperlink
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import java.io.File

class HelloApplication : Application() {
    var text = "The moving average (MA) is a simple technical analysis tool that smooths out price data by creating a constantly updated average price. The average is taken over a specific period of time, like 10 days, 20 minutes, 30 weeks, or any time period the trader chooses. There are advantages to using a moving average in your trading, as well as options on what type of moving average to use." +
            "Moving average strategies are also popular and can be tailored to any time frame, suiting both long-term investors and short-term traders."
    var listOfWords = text.split(" ", "?<=,", "?<=.")
    val translate: Translate = TranslateOptions.getDefaultInstance().toBuilder().setTargetLanguage("ru").build().service


    override fun start(stage: Stage) {
        println(listOfWords)

        val hyperlinkListText = listOfWords.map { it -> Hyperlink(it) }.toList()


        // set title for the stage
        // set title for the stage
        stage.title = "TextFlow"

        // create TextFlow

        // create TextFlow
        val textFlow = TextFlow(
            *hyperlinkListText.toTypedArray()
        )


        textFlow.children.map {
            if (it is Hyperlink) {
                it.setOnAction {
                    val h: Hyperlink = it.target as Hyperlink
                    val wordOfTarget = h.text

                    val indexOfTarget = textFlow.children.indexOf(h)
                    println("Clicked $h.text which has index $indexOfTarget")
                    val listOfWordsOfSentence = getWordListOfSentenceWithWord(indexOfTarget)
                    val sentence = listOfWordsOfSentence.joinToString(separator = " ")
                    println(sentence)

                    val translation = hke(wordOfTarget)
                    addWordToTxtFile(wordOfTarget, translation, sentence)
                }
            }
        }

//        val textFlow = TextFlow(
//            Text("Don't have an account? "), Hyperlink("Click here")
//        )


//        // create text
//
//        // create text
//        val text_1 = Text("GeeksforGeeks\n")
//
//        // set the text color
//
//        // set the text color
//        text_1.setFill(Color.RED)
//
//        // set font of the text
//
//        // set font of the text
//        text_1.setFont(Font.font("Verdana", 25.0))
//
//        // create text
//
//        // create text
//        val text_2 = Text("The computer science portal for geeks")
//
//        text_2.setFill(Color.BLUE)
//
//        // add text to textflow
//
//        textFlow.children.add(text_1)
//        textFlow.children.add(text_2)
//        textFlow.children.add(Text("sadf"))

        // create a scene

        // create a scene
        val scene = Scene(textFlow, 400.0, 300.0)
        scene.stylesheets.add(javaClass.getResource("stylesheet.css").toExternalForm())
//        scene.stylesheets.add("/stylesheet.css")

        // set the scene

        // set the scene
        stage.scene = scene

        stage.show()
    }

//    fun getSentenceWithWord(indexOfWord: Int) {
//        listOfWords.reversed().takeWhile { it.contains(".") }
//        listOfWords.takeWhileInclusive { it.contains(".") }
//
//    }

    fun getWordListOfSentenceWithWord(indexOfWord: Int): List<String> {
        var endIndex: Int = 0;
        var startIndex: Int = 0;

        for (i in indexOfWord until listOfWords.size) {
            if (listOfWords.get(i).contains(".") || i+1 == listOfWords.size) {
                endIndex = i;
                break
            }
        }

        for (i in indexOfWord downTo 0) {
            if (i==indexOfWord && listOfWords.get(i).contains(".")) {
                continue
            }
            if (listOfWords.get(i).contains(".")) {
                startIndex = i+1;
                break
            }
            if (i == 0) {
                startIndex = 0
                break
            }
        }

        println("startIndex $startIndex")
        println("endIndex $endIndex")

        val sentence = listOfWords.slice(startIndex..endIndex)

        println("Sentence: $sentence")

        return sentence
    }


    fun addWordToTxtFile(word: String, translation: String, cardback: String) {
        println("Writing to file")
        val readyWord = if (isLetters(word)) {
            word
        } else {
            //if contains , . ! ? etc.
            word.substring(0, word.length-1)
        }

        File("ankiFile.txt").appendText("\n$readyWord; $translation <br></br> $cardback")
    }

    fun hke(word: String): String {
//        val translate: Translate = TranslateOptions.getDefaultInstance().getService()

        val translation = translate.translate(word)
        val translation2 = translate.translate("this is a test")
        System.out.printf("Translated Text:\n\t%s\n", translation.translatedText)
        System.out.printf("Translated Text:\n\t%s\n", translation2.translatedText)

        return translation.translatedText
    }


    fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}

