package edu.augustana.model;

import edu.augustana.App;
import edu.augustana.ui.CreateLessonPlanController;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<LessonPlan> undoStack, redoStack;

    //private LessonPlan lessonPlan;

    public UndoRedoHandler(LessonPlan lessonPlan) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoStack.push(lessonPlan.clone());
    }

    public void saveState() {
        undoStack.push(App.getCurrentLessonPlan().clone());
        redoStack.clear();
        System.out.println("SAVE STATE CALLED, undo stack=");
        System.out.println(undoStack);
        System.out.println("------------------------------");
    }

    public void undo() {
        System.out.println("UNDO CALLED, undo stack=");
        System.out.println(undoStack);
        System.out.println("------------------------------");
        if (undoStack.size() == 1) {
//            System.out.println("size == 1 undo stack: " + undoStack);
//            System.out.println();
//            System.out.println(" size == 1 app: " + App.getCurrentLessonPlan());
//            System.out.println("-------------------------------------");
            return;
        } else {
            redoStack.push(undoStack.pop());
            System.out.println("UNDO CALLED, after popping, undo stack=");
            System.out.println(undoStack);
            System.out.println("------------------------------");
            System.out.println("undoStack.peek = " + undoStack.peek());
            System.out.println("------------------------------");
            System.out.println("undoStack.peek.clone() = " + undoStack.peek().clone());
            App.getCurrentLessonPlan().restoreState(undoStack.peek().clone());
//            System.out.println("undo peek after pop: " + undoStack.peek());
//            System.out.println();
//            System.out.println("app.getCurrentLessonPlan(): " + App.getCurrentLessonPlan());
//            System.out.println();

        }

    }

    public void redo() {
        System.out.println("REDO CALLED, undo stack=");
        System.out.println(undoStack);
        System.out.println("------------------------------");
        if (redoStack.isEmpty()) {
            return;
        } else {
            System.out.println("redo: " + redoStack);
            LessonPlan temp = redoStack.pop();
            App.getCurrentLessonPlan().restoreState(temp.clone());
            undoStack.push(temp);
            //System.out.println("redo :" + redoStack);
        }
    }

    public Stack<LessonPlan> getUndoStack() { return undoStack; }

}
