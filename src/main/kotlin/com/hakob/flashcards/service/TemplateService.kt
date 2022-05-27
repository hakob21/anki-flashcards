package com.hakob.flashcards.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class TemplateService {
    var index = 0
    fun createNewTepmlateAndReturn(richText: String): String {
        val filePathInResources = javaClass.getResource("/templates").file + "/newSaved${++index}.html"
        val f = File(filePathInResources)

        if (f.exists()) {
            f.delete()
        }
        f.createNewFile()
        f.writeText(richText)

        return "newSaved$index"
    }
}