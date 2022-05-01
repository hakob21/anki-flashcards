package com.hakob.flashcards.testPack

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.hakob.flashcards.testPack.HelloApplication.StageReadyEvent
import javafx.scene.Scene
import javafx.scene.control.Hyperlink
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.io.File


@Component
class StageInitializer : ApplicationListener<StageReadyEvent> {
    var text =
        "The moving average (MA) is a simple technical analysis tool that smooths out price data by creating a constantly updated average price. The average is taken over a specific period of time, like 10 days, 20 minutes, 30 weeks, or any time period the trader chooses. There are advantages to using a moving average in your trading, as well as options on what type of moving average to use." +
                "Moving average strategies are also popular and can be tailored to any time frame, suiting both long-term investors and short-term traders."
    var listOfWords = text.split(" ", "?<=,", "?<=.")

    @Autowired
    lateinit var translateUtils: TranslateUtils

    override fun onApplicationEvent(event: StageReadyEvent) {
        val stage: Stage? = event.getStage()

        println(listOfWords)

        val hyperlinkListText = listOfWords.map { it -> Hyperlink(it) }.toList()

        if (stage != null) {
            stage.title = "TextFlow"
        }

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

                    val translation = translateUtils.getTranslatedWord(wordOfTarget)
                    addWordToTxtFile(wordOfTarget, translation, sentence)
                }
            }
        }

       // create a scene
        val scene = Scene(textFlow, 400.0, 300.0)
        scene.stylesheets.add(javaClass.getResource("/com/hakob/flashcards/stylesheet.css").toExternalForm())

        if (stage != null) {
            stage.scene = scene
        }

        if (stage != null) {
            stage.show()
        }
    }

    fun getWordListOfSentenceWithWord(indexOfWord: Int): List<String> {
        var endIndex: Int = 0;
        var startIndex: Int = 0;

        for (i in indexOfWord until listOfWords.size) {
            if (listOfWords.get(i).contains(".") || i + 1 == listOfWords.size) {
                endIndex = i;
                break
            }
        }

        for (i in indexOfWord downTo 0) {
            if (i == indexOfWord && listOfWords.get(i).contains(".")) {
                continue
            }
            if (listOfWords.get(i).contains(".")) {
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
            word.substring(0, word.length - 1)
        }

        File("ankiFile.txt").appendText("\n$readyWord; $translation <br></br> $cardback")
    }



    fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }
}