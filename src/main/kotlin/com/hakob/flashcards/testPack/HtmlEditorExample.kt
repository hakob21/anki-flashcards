import com.hakob.flashcards.testPack.HelloApplication
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage
import javafx.geometry.Pos
import javafx.scene.control.Button





class HtmlEditorExample : Application() {
    override fun start(primaryStage: Stage) {
        val htmlEditor = HTMLEditor()
        val vBox = VBox(htmlEditor)
        val scene = Scene(vBox)
        primaryStage.scene = scene
        primaryStage.title = "JavaFX App"
        primaryStage.show()
        val showHTMLButton = Button("Produce HTML Code")
        vBox.setAlignment(Pos.CENTER)
        showHTMLButton.setOnAction {
            println("Click")
        }
    }
}

fun main() {
    Application.launch(HtmlEditorExample::class.java)
}
