package com.hakob.flashcards.testPack

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

//    @Autowired
//    lateinit var applicationStarter: ApplicationStarter

    @Autowired
    lateinit var translateUtils: TranslateUtils

    @Autowired
    lateinit var sceneUtils: SceneUtils

    override fun onApplicationEvent(event: StageReadyEvent) {
        val stage: Stage = event.getStage()

        val listOfWords = text.split(" ", "?<=,", "?<=.")

        val hyperLinkWordList = listOfWords.map { it -> Hyperlink(it) }.toList()

        // create TextFlow
        val textFlow = TextFlow(
            *hyperLinkWordList.toTypedArray()
        )

        textFlow.children.map {
            if (it is Hyperlink) {
                it.setOnAction {
                    val h: Hyperlink = it.target as Hyperlink
                    val wordOfTarget = h.text

                    val indexOfTarget = textFlow.children.indexOf(h)
                    println("Clicked $h.text which has index $indexOfTarget")
                    val sentence = translateUtils.getWordsFromSentence(indexOfWord = indexOfTarget, listOfWordsFromText = listOfWords)

                    val translation = translateUtils.getTranslatedWord(wordOfTarget)
                    addWordToTxtFile(wordOfTarget, translation, sentence)
                }
            }
        }

       // create a scene
        sceneUtils.createScene(textFlow, stage)
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