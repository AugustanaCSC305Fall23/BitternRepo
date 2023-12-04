package edu.augustana.model;

import edu.augustana.App;
import edu.augustana.ui.CreateLessonPlanController;

import java.util.Stack;

/**
 * Based a lot of this code on the undoRedoHandler in MovieTrackerApp
 */
public class UndoRedoHandler {
    private Stack<LessonPlan> undoStack, redoStack;

    private LessonPlan lessonPlan;

    public UndoRedoHandler(LessonPlan lessonPlan) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        this.lessonPlan = lessonPlan;
        undoStack.push(lessonPlan);
    }

    public void saveState() {
        undoStack.push(App.getCurrentLessonPlan());
        redoStack.clear();
    }

    public void undo() {
        System.out.println("app: " + App.getCurrentLessonPlan());
        System.out.println();
        System.out.println("undo stack: " + undoStack);
        System.out.println();
        if (undoStack.size() == 1) {
            System.out.println("size == 1 undo stack: " + undoStack);
            System.out.println();
            System.out.println(" size == 1 app: " + App.getCurrentLessonPlan());
            System.out.println("-------------------------------------");
            return;
        } else {
            redoStack.push(undoStack.pop());
            App.setCurrentLessonPlan(undoStack.peek());
            System.out.println("undo peek after pop: " + undoStack.peek());
            System.out.println("app: " + App.getCurrentLessonPlan());
            System.out.println();
            System.out.println("undo stack: " + undoStack);
            System.out.println("{------------------------------");
        }

    }

    public void redo() {
        System.out.println(redoStack);
        if (redoStack.isEmpty()) {
            return;
        } else {
            App.setCurrentLessonPlan(redoStack.pop());
            undoStack.push(App.getCurrentLessonPlan());
            System.out.println(App.getCurrentLessonPlan());
        }
    }

}
