package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseTitle;
    private List<LessonPlan> lessonPlanList;

    public Course() {
        courseTitle = "Course Title";
        lessonPlanList = new ArrayList<>();
        lessonPlanList.add(new LessonPlan("My Lesson Plan"));
    }

    public LessonPlan createNewLessonPlan() {
        LessonPlan newLessonPlan = new LessonPlan("My Lesson Plan");
        return newLessonPlan;
    }

    public void save() {

    }

    public void load() {

    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseTitle='" + courseTitle + '\'' +
                ", lessonPlanList=" + lessonPlanList +
                '}';
    }
}
