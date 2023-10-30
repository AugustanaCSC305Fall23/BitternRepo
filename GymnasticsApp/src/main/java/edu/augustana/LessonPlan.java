package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private static List<Card> lessonPlanList = new ArrayList<>();

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
    }
    public List<Card> getLessonPlanList(){
        return lessonPlanList;
    }

    public void addCardToList(Card card){
        lessonPlanList.add(card);
    }

    @Override
    public String toString() {
        return "Title of Lesson Plan: " + title;
    }
}
