package edu.augustana.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course
 */
public class Course implements Cloneable, Undoable {
    private String title;
    private ArrayList<LessonPlan> lessonPlanList;

    /**
     * Creates a new course
     */
    public Course() {
        lessonPlanList = new ArrayList<>();
        title = "Untitled";
    }

    /**
     * Creates a new lesson plan and adds it to the course's lessonPlanList
     * @return The new lesson plan
     */
    public LessonPlan createNewLessonPlan() {
        LessonPlan newLessonPlan = new LessonPlan();
        this.getLessonPlanList().add(newLessonPlan);
        return newLessonPlan;
    }

    public String getTitle() {
        return title;
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    @Override
    public Undoable clone() {
        Course clone = new Course();
        clone.title = this.getTitle();
        clone.lessonPlanList = new ArrayList<>();
        for (LessonPlan lessonPlan : lessonPlanList) {
            clone.lessonPlanList.add(lessonPlan.clone());
        }
        return clone;
    }

    @Override
    public void restoreState(Undoable copyOfPreviousState) {
        Course copyOfPreviousCourseState = (Course) copyOfPreviousState;
        this.title = copyOfPreviousCourseState.title;
        this.lessonPlanList = copyOfPreviousCourseState.lessonPlanList;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", lessonPlanList=" + lessonPlanList +
                '}';
    }
}
