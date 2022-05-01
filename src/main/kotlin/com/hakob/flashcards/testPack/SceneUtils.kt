package com.hakob.flashcards.testPack

import javafx.scene.Scene
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import org.springframework.stereotype.Service

@Service
class SceneUtils {
    fun createScene(textFlow: TextFlow, stage: Stage) {
        stage.title = "TextFlow"

        val scene = Scene(textFlow, 400.0, 300.0)
        scene.stylesheets.add(javaClass.getResource("/com/hakob/flashcards/stylesheet.css").toExternalForm())

        stage.scene = scene

        stage.show()
    }
}