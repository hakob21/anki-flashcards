package com.hakob.flashcards.testPack

import com.hakob.flashcards.utils.SceneUtils
import com.hakob.flashcards.utils.TranslateUtils
import com.sun.javafx.geom.Rectangle
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.TextFlow
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File


@Service
class JavaFxAppStarter {
    var text =
        "The moving average (MA) is a simple technical analysis tool that smooths out price data by creating a constantly updated average price. The average is taken over a specific period of time, like 10 days, 20 minutes, 30 weeks, or any time period the trader chooses. There are advantages to using a moving average in your trading, as well as options on what type of moving average to use." +
                "Moving average strategies are also popular and can be tailored to any time frame, suiting both long-term investors and short-term traders."

    @Autowired
    lateinit var translateUtils: TranslateUtils

    @Autowired
    lateinit var sceneUtils: SceneUtils

    fun runApp(stage: Stage) {
        stage.title = "HTMLEditor Sample"
        stage.width = 650.0
        stage.height = 500.0
        val scene = Scene(Group())
        var root = VBox()
        root.padding = Insets(8.0, 8.0, 8.0, 8.0)
        root.spacing = 5.0
        root.alignment = Pos.BOTTOM_LEFT
        val htmlEditor = HTMLEditor()
        htmlEditor.prefHeight = 245.0
        htmlEditor.htmlText = "textTest"
        val htmlCode = TextArea()
        htmlCode.setWrapText(true)
        val scrollPane = ScrollPane()
        scrollPane.getStyleClass().add("noborder-scroll-pane")
        scrollPane.setContent(htmlCode)
        scrollPane.setFitToWidth(true)
        scrollPane.setPrefHeight(180.0)
        val showHTMLButton = Button("Produce HTML Code")
        root.alignment = Pos.CENTER
        //button
//        showHTMLButton.setOnAction { arg0: ActionEvent? -> htmlCode.setText(htmlEditoor.htmlText) }
        showHTMLButton.setOnAction(object : EventHandler<ActionEvent?> {
            override fun handle(event: ActionEvent?) {
                val secondLabel = Label("I'm a Label on new Window")
                val secondaryLayout = StackPane()
                secondaryLayout.children.add(secondLabel)
                val secondScene = Scene(secondaryLayout, 230.0, 100.0)

                // New window (Stage)
                val newWindow = Stage()
                newWindow.title = "Second Stage"
                newWindow.scene = secondScene

                // Set position of second window, related to primary window.
                newWindow.x = stage.getX() + 200
                newWindow.y = stage.getY() + 100
                newWindow.show()
            }
        })

        root.children.addAll(htmlEditor, showHTMLButton, scrollPane)
        scene.root = root
        stage.scene = scene
        stage.show()






//        val listOfWords = text.split(" ", "?<=,", "?<=.")
//        val hyperLinkWordList = listOfWords.map { Hyperlink(it) }.toList()
//
        // create TextFlow
//        val textFlow = createTextFlow(hyperLinkWordList, listOfWords)

        // create a scene
//        sceneUtils.createScene(textFlow, stage)
    }

    private fun createTextFlow(
        hyperLinkWordList: List<Hyperlink>,
        listOfWords: List<String>
    ): TextFlow {
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
                    val sentence = translateUtils.getWordsFromSentence(
                        indexOfWord = indexOfTarget,
                        listOfWordsFromText = listOfWords
                    )

                    val translation = translateUtils.getTranslatedWord(wordOfTarget)
                    addWordToTxtFile(wordOfTarget, translation, sentence)
                }
            }
        }
        return textFlow
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

    private fun isLetters(string: String): Boolean {
        return string.all { it.isLetter() }
    }
}