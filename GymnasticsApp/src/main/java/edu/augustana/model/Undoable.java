package edu.augustana.model;

public interface Undoable {

    /**
     * Creates and returns a deep copy of the Undoable it is called on.
     * @return a deep copy of an Undoable
     */
    public Undoable clone();

    /**
     * Reverts the Undoable it is called on to a previous state
     * @param copyOfPreviousState a deep copy of the Undoable state you want to restore
     */
    public void restoreState(Undoable copyOfPreviousState);
}
