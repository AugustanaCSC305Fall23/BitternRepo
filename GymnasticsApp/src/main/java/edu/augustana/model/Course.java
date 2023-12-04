package edu.augustana.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseTitle;
    private ArrayList<LessonPlan> lessonPlanList;

    public Course() {
        lessonPlanList = new ArrayList<>();
        //lessonPlanList.add(new LessonPlan("My Lesson Plan"));
    }

    public LessonPlan createNewLessonPlan() {
        LessonPlan newLessonPlan = new LessonPlan();
        this.getLessonPlanList().add(newLessonPlan);
        return newLessonPlan;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    public void setTitle(String newTitle) {
        courseTitle = newTitle;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseTitle='" + courseTitle + '\'' +
                ", lessonPlanList=" + lessonPlanList +
                '}';
    }
}
