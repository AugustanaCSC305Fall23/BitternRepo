package edu.augustana;

public class LessonPlan {
    private String title;

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
    }

    @Override
    public String toString() {
        return "Title of Lesson Plan: " + title;
    }
}
