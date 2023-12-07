package edu.augustana.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String title;
    private ArrayList<LessonPlan> lessonPlanList;

    public Course() {
        lessonPlanList = new ArrayList<>();
        title = "Untitled";
    }

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
    public String toString() {
        return "Course{" +
                "courseTitle='" + title + '\'' +
                ", lessonPlanList=" + lessonPlanList +
                '}';
    }
}
