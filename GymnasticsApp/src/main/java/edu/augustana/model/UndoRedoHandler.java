package edu.augustana.model;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<LessonPlan> undoStack, redoStack;

    private LessonPlan lessonPlan;

    public UndoRedoHandler(LessonPlan lessonPlan) {
        undoStack = new Stack<LessonPlan>();
        redoStack = new Stack<LessonPlan>();
        this.lessonPlan = lessonPlan;
        undoStack.push(lessonPlan);
    }

    public void undo() {
        if (undoStack.size() == 1) {
            return;
        } else {

        }

    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        } else {

        }
    }

}
