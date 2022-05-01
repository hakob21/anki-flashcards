import javafx.application.Application
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage


class HtmlEditorSample : Application() {

    private val INITIAL_TEXT = ("Lorem ipsum dolor sit "
            + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
            + "in scelerisque cursus, pulvinar at ante. Nulla consequat"
            + "congue lectus in sodales. Nullam eu est a felis ornare "
            + "bibendum et nec tellus. Vivamus non metus tempus augue auctor "
            + "ornare. Duis pulvinar justo ac purus adipiscing pulvinar. "
            + "Integer congue faucibus dapibus. Integer id nisl ut elit "
            + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
            + "sem.")

    override fun start(stage: Stage) {
        stage.title = "HTMLEditor Sample"
        stage.width = 650.0
        stage.height = 500.0
        val scene = Scene(Group())
        val root = VBox()
        root.padding = Insets(8.0, 8.0, 8.0, 8.0)
        root.spacing = 5.0
        root.alignment = Pos.BOTTOM_LEFT
        val htmlEditor = HTMLEditor()
        htmlEditor.prefHeight = 245.0
        htmlEditor.htmlText = INITIAL_TEXT
        val htmlCode = TextArea()
        htmlCode.setWrapText(true)
        val scrollPane = ScrollPane()
        scrollPane.getStyleClass().add("noborder-scroll-pane")
        scrollPane.setContent(htmlCode)
        scrollPane.setFitToWidth(true)
        scrollPane.setPrefHeight(180.0)
        val showHTMLButton = Button("Produce HTML Code")
        root.alignment = Pos.CENTER


        showHTMLButton.setOnAction { arg0: ActionEvent? -> htmlCode.setText(htmlEditor.htmlText) }
        root.children.addAll(htmlEditor, showHTMLButton, scrollPane)
        scene.root = root
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(HtmlEditorSample::class.java)
}