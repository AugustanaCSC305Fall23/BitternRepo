package edu.augustana.model;

import edu.augustana.App;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<LessonPlan> undoStack, redoStack;

    public UndoRedoHandler(LessonPlan lessonPlan) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoStack.push(lessonPlan.clone());
    }

    public void saveState() {
        undoStack.push(App.getCurrentLessonPlan().clone());
        redoStack.clear();
    }

    public void undo() {
        if (undoStack.size() == 1) {
            return;
        } else {
            redoStack.push(undoStack.pop());
            App.getCurrentLessonPlan().restoreState(undoStack.peek().clone());
        }
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        } else {
            LessonPlan temp = redoStack.pop();
            App.getCurrentLessonPlan().restoreState(temp.clone());
            undoStack.push(temp);
        }
    }

    public Stack<LessonPlan> getUndoStack() { return undoStack; }

}
