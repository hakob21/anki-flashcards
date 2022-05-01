package com.hakob.flashcards.testPack

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.text.Text
import javafx.scene.text.TextFlow

class HelloController {
    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private lateinit var text: TextFlow

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
        println(text.children)
    }
}