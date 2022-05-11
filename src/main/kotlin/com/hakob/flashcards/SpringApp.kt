package com.hakob.flashcards

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringApp

fun main(args: Array<String>) {
    runApplication<SpringApp>(*args) {
    }
}