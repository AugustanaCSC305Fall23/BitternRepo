package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseTitle;
    private List<LessonPlan> lessonPlanList;

    public Course(String title) {
        courseTitle = title;
        lessonPlanList = new ArrayList<>();
    }

    public void addLessonPlan(LessonPlan lessonPlan) {

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
