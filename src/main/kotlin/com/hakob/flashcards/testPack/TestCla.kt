package com.hakob.flashcards.testPack

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TestCla {
    @Autowired
    lateinit var helloApplication: HelloApplication
    @PostConstruct
    fun add() {
        println(helloApplication.text)
    }
}