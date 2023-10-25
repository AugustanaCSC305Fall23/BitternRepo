package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class CourseCollection {
    private List<Course> courseList;
    public CourseCollection() {
        courseList = new ArrayList<>();
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course newCourse) {
        courseList.add(newCourse);
    }
}
