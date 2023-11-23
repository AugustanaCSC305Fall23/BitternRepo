package edu.augustana.ui;

import edu.augustana.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class TitleEditor {
    private final TextField titleField;
    private final Font editingFont;
    private final Font titleFont;

    public TitleEditor(TextField titleField, Font editingFont, Font titleFont) {
       this.titleField = titleField;
       this.editingFont = editingFont;
       this.titleFont = titleFont;
    }

    @FXML public void editTitle() {
        titleField.setFont(editingFont);
        titleField.setEditable(true);
        if (titleField.getText().equals(titleField.getPromptText())) {
            titleField.clear();
        }
    }

    @FXML public void lockInTitle() {
        titleField.setFont(titleFont);
        if (!titleField.getText().isEmpty()) {
            titleField.setEditable(false);
        } else {
            giveWarning("Cannot have empty title.");
        }
    }

    @FXML private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
