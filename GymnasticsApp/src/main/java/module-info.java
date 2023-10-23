module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.opencsv;
    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
