package com.hakob.flashcards.testPack

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.hakob.flashcards.SpringApp
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Hyperlink
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.File
import javax.annotation.PostConstruct

@Component
class HelloApplication : Application() {
    lateinit var applicationContext: ConfigurableApplicationContext

    override fun init() {
        applicationContext = SpringApplicationBuilder(SpringApp::class.java).run()
        super.init()
    }

    override fun stop() {
        applicationContext.close()
        Platform.exit()
    }

    class StageReadyEvent(stage: Stage?) : ApplicationEvent(stage) {
        fun getStage(): Stage {
            return getSource() as Stage
        }
    }

    override fun start(stage: Stage) {
        applicationContext.publishEvent(StageReadyEvent(stage))
   }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}

