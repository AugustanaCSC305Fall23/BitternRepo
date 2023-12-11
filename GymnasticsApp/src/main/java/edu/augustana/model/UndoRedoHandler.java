package edu.augustana.model;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<Undoable> undoStack, redoStack;

    /**
     * Contructs an UndoRedoHandler object
     * @param undoable the Undoable to 
     */
    public UndoRedoHandler(Undoable undoable) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoStack.push(undoable.clone());
    }

    /**
     * Stores the passed-in Undoable state in the undo stack and clears the redo stack
     * @param state The Undoable state to store
     */
    public void saveState(Undoable state) {
        undoStack.push(state);
        redoStack.clear();
    }

    /**
     * Undoes the previous Undoable state
     * @param undoableToUndo The Undoable to undo
     */
    public void undo(Undoable undoableToUndo) {
        if (undoStack.size() != 1) {
            redoStack.push(undoStack.pop());
            undoableToUndo.restoreState(undoStack.peek().clone());
        }
    }

    /**
     * Reverts the previous undo
     * @param undoableToRedo The Undoable to revert
     */
    public void redo(Undoable undoableToRedo) {
        if (!redoStack.isEmpty()) {
            Undoable temp = redoStack.pop();
            undoableToRedo.restoreState(temp.clone());
            undoStack.push(temp);
        }
    }

    @Override
    public String toString() {
        return "UndoRedoHandler{" +
                "undoStack=" + undoStack +
                ", redoStack=" + redoStack +
                '}';
    }
}
