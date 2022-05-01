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
    @Autowired
    lateinit var javaFxAppStarter: JavaFxAppStarter

    override fun onApplicationEvent(event: StageReadyEvent) {
        javaFxAppStarter.runApp(event.getStage())
    }
}