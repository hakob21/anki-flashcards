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

    var text = "The moving average (MA) is a simple technical analysis tool that smooths out price data by creating a constantly updated average price. The average is taken over a specific period of time, like 10 days, 20 minutes, 30 weeks, or any time period the trader chooses. There are advantages to using a moving average in your trading, as well as options on what type of moving average to use." +
            "Moving average strategies are also popular and can be tailored to any time frame, suiting both long-term investors and short-term traders."
    var listOfWords = text.split(" ", "?<=,", "?<=.")
    val translate: Translate = TranslateOptions.getDefaultInstance().toBuilder().setTargetLanguage("ru").build().service

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

