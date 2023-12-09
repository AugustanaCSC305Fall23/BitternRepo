package edu.augustana.model;

public interface Undoable {
    public Undoable clone();
    public void restoreState(Undoable copyOfPreviousState);
}
