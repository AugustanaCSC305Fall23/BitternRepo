package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private List<Card> lessonPlanCardList = new ArrayList<>();

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
    }
    public List<Card> getLessonPlanCardList(){
        return lessonPlanCardList;
    }

    public void addCardToList(Card card){
        lessonPlanCardList.add(card);
    }

    @Override
    public String toString() {
        return title;
    }
}
