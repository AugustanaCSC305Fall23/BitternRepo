package edu.augustana.ui;

import edu.augustana.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
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

    public void setUpTitle() {
        if (App.getCurrentCourse().getCourseTitle() != null) {
            titleField.setText(App.getCurrentCourse().getCourseTitle());
            titleField.setStyle("-fx-text-fill: white;" + "-fx-background-color: transparent");
            titleField.setFont(new Font("Britannic Bold", 45.0));
        } else {
            titleField.setText(titleField.getPromptText());
            titleField.setStyle("-fx-text-fill: lightGray;" + "-fx-background-color: transparent");
            titleField.setFont(new Font("System Italic", 45.0));
        }
        titleField.setOnMouseClicked(e -> editTitle());
        titleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                lockInTitle();
                if (!titleField.getText().equals(titleField.getPromptText())) {
                    App.getCurrentCourse().setTitle(titleField.getText());
                } else {
                    App.getCurrentCourse().setTitle(null);
                }
            }
        });
    }

    public void editTitle() {
        titleField.setFont(editingFont);
        titleField.setStyle("-fx-text-fill: white;" + "-fx-background-color: transparent");
        titleField.setEditable(true);
        if (titleField.getText().equals(titleField.getPromptText())) {
            titleField.clear();
        }
    }

    public void lockInTitle() {
        titleField.setFont(titleFont);
        if (titleField.getText().isEmpty()) {
            titleField.setText(titleField.getPromptText());
            titleField.setStyle("-fx-text-fill: lightGray;" + "-fx-background-color: transparent");
            titleField.setFont(new Font("System Italic", titleFont.getSize()));
            giveWarning("Cannot have empty title.");
        }
        titleField.setEditable(false);
    }

    private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
