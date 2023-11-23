package edu.augustana.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ButtonControl {

    private int sizeIncrement;

    public ButtonControl(int sizeIncrement) {
        this.sizeIncrement = sizeIncrement;
    }
    @FXML
    public void enlargeButton(Button btn) {
        btn.setFont(new Font(btn.getFont().getName(), btn.getFont().getSize() + 1));
        btn.setPrefSize(btn.getWidth() + sizeIncrement, btn.getHeight() + sizeIncrement);
    }

    @FXML
    public void resetButton(Button btn) {
        btn.setFont(new Font(btn.getFont().getName(), btn.getFont().getSize() - 1));
        btn.setPrefSize(btn.getWidth() - sizeIncrement, btn.getHeight() - sizeIncrement);
    }

}