package edu.augustana.model;

import java.util.Stack;
public class UndoRedoHandler {
    private Stack<LessonPlan> undoStack, redoStack;

    private LessonPlan lessonPlan;

    public UndoRedoHandler(LessonPlan lessonPlan) {
        undoStack = new Stack<LessonPlan>();
        redoStack = new Stack<LessonPlan>();
        this.lessonPlan = lessonPlan;
        undoStack.push(lessonPlan);
    }


}
