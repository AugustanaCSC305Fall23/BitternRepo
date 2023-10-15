module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
