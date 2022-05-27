package com.hakob.flashcards.frontendController

import com.hakob.flashcards.service.MainService
import com.hakob.flashcards.service.SubmitFormTh
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.io.File


@Controller
@RequestMapping("/index")
class FrontendController(
    val mainService: MainService,

    @Value("\${server.port}")
    var localPort: Int,
) {
    @GetMapping
    fun mainPage(model: Model): String {
        println("HeyPost: $localPort")
        model.addAttribute("submitForm", SubmitFormTh())
        return "index"
    }

    @PostMapping
    fun save(submitFormTh: SubmitFormTh, model: Model): String? {
//        model.addAttribute("localPort", localPort)
//        model.addAttribute("testVal", "myTestValue")

        val richTextWithoutLinks: String = mainService.submitPageAndReturnGeneratedPage(submitFormTh.content)
//        val newSubmitFormTh = SubmitFormTh("tt", richTextWithoutLinks)
//        model.addAttribute("submitForm", newSubmitFormTh)
        val finalText = richTextWithoutLinks.plus("""
            <script th:inline="javascript">
                var port = [[            \${localPort}            ]]
                console.log("localporrrtt: " + port)
                var tag = document.getElementsByName("word");
                // const max = tag.length;
                console.log(tag.length)
                for (var i1 = 0; i1 < tag.length; i1++){
                    var item = tag[i1];
                    item.addEventListener('click', function (event) {
                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", "http://localhost:" + port + "/translate", true);
                            xhr.setRequestHeader('Content-Type', 'application/json');
                            xhr.send(JSON.stringify({
                                indexOfWordToTranslate: this.id,
                                wordToTranslate: document.getElementById(this.id).textContent
                            }));
                            console.log("Final array " + document.getElementById(this.id).textContent)

                        xhr.onreadystatechange = function() {
                            if (xhr.readyState == XMLHttpRequest.DONE) {
                                console.log(xhr.responseText)
                                var toastDiv = document.querySelector('#toast-body')
                                toastDiv.innerHTML = xhr.responseText

                                var toast = new bootstrap.Toast(document.querySelector('#basicToast'))
                                toast.show();
                            }
                        }
                            // console.log("Final array " + document.getElementById(this.id))
                        }
                    )
                }
                document.querySelector("#basicToastBtn").onclick = function() {
                    new bootstrap.Toast(document.querySelector('#basicToast')).show();
                }
            </script>
        """.trimIndent())

        val file = File(javaClass.getResource("/templates/newSaved.html").file)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        file.writeText(finalText)

        return "newSaved"
//        return "saved"
    }
}