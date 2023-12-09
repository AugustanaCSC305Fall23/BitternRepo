package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.UndoRedoHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class TitleEditor {
    private final TextField titleField;
    private final Font editingFont;
    private final Font titleFont;
    private final char courseOrLessonPlan;

    public TitleEditor(TextField titleField, Font editingFont, Font titleFont, char courseOrLessonPlan) {
       this.titleField = titleField;
       this.editingFont = editingFont;
       this.titleFont = titleFont;
       this.courseOrLessonPlan = courseOrLessonPlan;
    }

    public void initializeTitleFieldEvents(UndoRedoHandler undoRedoHandler) {
        titleField.setOnMouseClicked(e -> editTitle());
        titleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                lockInTitle();
                undoRedoHandler.saveState();
            }
        });
    }

    public void setTitleFieldText() {
        if (courseOrLessonPlan == 'L' && !App.getCurrentLessonPlan().getTitle().equals("Untitled")) {
            displayActualTitle();
        } else if (courseOrLessonPlan == 'C' && !App.getCurrentCourse().getTitle().equals("Untitled")){
            displayActualTitle();
        } else {
            displayPromptText();
        }
    }

    private void editTitle() {
        titleField.setFont(editingFont);
        titleField.setEditable(true);
        if (titleField.getText().equals(titleField.getPromptText())) {
            titleField.clear();
        }
    }

    private void lockInTitle() {
        titleField.setFont(titleFont);
        if (titleField.getText().isEmpty()) {
            displayPromptText();
            if (courseOrLessonPlan == 'L') {
                App.getCurrentLessonPlan().setTitle("Untitled");
            } else if (courseOrLessonPlan == 'C') {
                App.getCurrentCourse().setTitle("Untitled");
            }
            giveWarning("Cannot have empty title.");
        } else {
            if (courseOrLessonPlan == 'L') {
                App.getCurrentLessonPlan().setTitle(titleField.getText());
            } else if (courseOrLessonPlan == 'C') {
                App.getCurrentCourse().setTitle(titleField.getText());
            }
        }
        titleField.setEditable(false);
    }

    private void displayActualTitle() {
        if (courseOrLessonPlan == 'L') {
            titleField.setText(App.getCurrentLessonPlan().getTitle());
        }  else if (courseOrLessonPlan == 'C') {
            titleField.setText(App.getCurrentCourse().getTitle());
        }
        titleField.setStyle("-fx-text-fill: white;" + "-fx-background-color: transparent");
        titleField.setFont(titleFont);
    }

    private void displayPromptText() {
        titleField.setText(titleField.getPromptText());
        titleField.setStyle("-fx-text-fill: lightGray;" + "-fx-background-color: transparent");
        titleField.setFont(new Font("System Italic", titleFont.getSize()));
    }

    private void giveWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
