module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.opencsv;
    requires com.google.gson;
    opens edu.augustana to javafx.fxml, com.google.gson;
    //opens java.io to com.google.gson;
    exports edu.augustana;
    exports edu.augustana.filters;
    opens edu.augustana.filters to javafx.fxml;
    exports edu.augustana.model;
    opens edu.augustana.model to com.google.gson, javafx.fxml;
    exports edu.augustana.ui;
    opens edu.augustana.ui to com.google.gson, javafx.fxml;
}
