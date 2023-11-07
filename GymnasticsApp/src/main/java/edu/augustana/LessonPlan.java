package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private List<Card> lessonPlanCardList = new ArrayList<>();
    private boolean isSaved = false;

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
        isSaved = false;
    }
    public List<Card> getLessonPlanCardList(){
        return lessonPlanCardList;
    }

    public void addCardToList(Card card){
        lessonPlanCardList.add(card);
        isSaved = false;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void changeIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    @Override
    public String toString() {
        return title;
    }
}
