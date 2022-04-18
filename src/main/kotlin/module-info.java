module com.hakob.flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.hakob.flashcards to javafx.fxml;
    exports com.hakob.flashcards;
}