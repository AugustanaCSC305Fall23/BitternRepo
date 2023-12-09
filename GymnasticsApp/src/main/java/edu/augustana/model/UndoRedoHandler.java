package edu.augustana.model;

import edu.augustana.App;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<Undoable> undoStack, redoStack;

    public UndoRedoHandler(Undoable undoable) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoStack.push(undoable.clone());
    }

    public void saveState(Undoable state) {
        undoStack.push(state);
        redoStack.clear();
    }

    public void undo(Undoable stateToUndo) {
        if (undoStack.size() != 1) {
            redoStack.push(undoStack.pop());
            stateToUndo.restoreState(undoStack.peek().clone());
        }
    }

    public void redo(Undoable stateToRedo) {
        if (!redoStack.isEmpty()) {
            Undoable temp = redoStack.pop();
            stateToRedo.restoreState(temp.clone());
            undoStack.push(temp);
        }
    }

    public Stack<Undoable> getUndoStack() { return undoStack; }

    @Override
    public String toString() {
        return "UndoRedoHandler{" +
                "undoStack=" + undoStack +
                ", redoStack=" + redoStack +
                '}';
    }
}
