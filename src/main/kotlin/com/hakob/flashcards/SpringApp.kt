package com.hakob.flashcards

import com.hakob.flashcards.testPack.HelloApplication
import javafx.application.Application
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//@ComponentScan(basePackages = [  "com.hakob.flashcards"  ])
open class SpringApp

fun main(args: Array<String>) {
    Application.launch(HelloApplication::class.java)
//    runApplication<HelloApplication>(*args) {
//    }
}