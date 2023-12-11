package edu.augustana.ui;

import edu.augustana.App;
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

    /**
     * Sets the on action events for the title field, so that when the mouse is clicked on
     * the title field it becomes editable, and when the Enter key is pressed the text that the user
     * has typed is locked in
     */
    public void initializeTitleFieldEvents() {
        titleField.setOnMouseClicked(e -> editTitle());
        titleField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                lockInTitle();
            }
        });
    }

    /**
     * Set the text of the title field to be the prompt text if the current lesson plan
     * or current course is untitled. Otherwise, sets the text to be the current lesson plan title
     * or current course, depending on if the user is in the lesson plan creator or the course view
     */
    public void setTitleFieldText() {
        if (courseOrLessonPlan == 'L' && !App.getCurrentLessonPlan().getTitle().equals("Untitled")) {
            displayActualTitle();
        } else if (courseOrLessonPlan == 'C' && !App.getCurrentCourse().getTitle().equals("Untitled")){
            displayActualTitle();
        } else {
            displayPromptText();
        }
    }

    // Sets the title field to be editable
    private void editTitle() {
        titleField.setFont(editingFont);
        titleField.setEditable(true);
        if (titleField.getText().equals(titleField.getPromptText())) {
            titleField.clear();
        }
    }

    /**
     * Sets the current lesson plan title or current course title, depending what screen the
     * user is currently on, to be the text typed in the text field. Sets the text field to not be editable.
     * Gives a warning if the user tries to enter an empty title.
      */
    private void lockInTitle() {
        titleField.setFont(titleFont);
        if (titleField.getText().isEmpty()) {
            displayPromptText();
            if (courseOrLessonPlan == 'L') {
                App.getCurrentLessonPlan().setTitle("Untitled");
            } else if (courseOrLessonPlan == 'C') {
                App.getCurrentCourse().setTitle("Untitled");
            }
            App.giveWarning("Cannot have empty title.");
        } else {
            if (courseOrLessonPlan == 'L') {
                App.getCurrentLessonPlan().setTitle(titleField.getText());
            } else if (courseOrLessonPlan == 'C') {
                App.getCurrentCourse().setTitle(titleField.getText());
            }
        }
        titleField.setEditable(false);
    }

    // Displays the title of the lesson plan or course in the text field
    private void displayActualTitle() {
        if (courseOrLessonPlan == 'L') {
            titleField.setText(App.getCurrentLessonPlan().getTitle());
        }  else if (courseOrLessonPlan == 'C') {
            titleField.setText(App.getCurrentCourse().getTitle());
        }
        titleField.setStyle("-fx-text-fill: white;" + "-fx-background-color: transparent");
        titleField.setFont(titleFont);
    }

    // Displays the prompt text in the text field for when the current title is Untitled
    private void displayPromptText() {
        titleField.setText(titleField.getPromptText());
        titleField.setStyle("-fx-text-fill: lightGray;" + "-fx-background-color: transparent");
        titleField.setFont(new Font("System Italic", titleFont.getSize()));
    }
}
