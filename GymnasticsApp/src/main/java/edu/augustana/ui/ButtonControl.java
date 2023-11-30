package edu.augustana.ui;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ButtonControl {

    private int sizeIncrement;

    public ButtonControl(int sizeIncrement) {
        this.sizeIncrement = sizeIncrement;
    }
    public void enlargeButton(Button btn) {
        btn.setFont(new Font(btn.getFont().getName(), btn.getFont().getSize() + sizeIncrement));
    }

    public void resetButton(Button btn) {
        btn.setFont(new Font(btn.getFont().getName(), btn.getFont().getSize() - sizeIncrement));
    }

}