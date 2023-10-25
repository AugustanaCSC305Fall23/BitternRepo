package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseTitle;
    private List<LessonPlan> lessonPlanList;

    public Course(String title) {
        courseTitle = title;
        lessonPlanList = new ArrayList<>();
        lessonPlanList.add(new LessonPlan("My Lesson Plan"));
    }

    public void addLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.add(lessonPlan);
    }

    public LessonPlan createNewLessonPlan() {
        LessonPlan newLessonPlan = new LessonPlan("My Lesson Plan");
        lessonPlanList.add(newLessonPlan);
        return newLessonPlan;
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
