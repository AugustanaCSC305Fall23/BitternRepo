package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private static List<Card> lessonPlanList = new ArrayList<>();
    private boolean isSaved;

    public LessonPlan(String title) {
        this.title = title;
        isSaved = false;
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String newTitle) {
        title = newTitle;
        isSaved = false;
    }
    public List<Card> getLessonPlanList(){
        return lessonPlanList;
    }

    public boolean getIsSaved() { return isSaved; }

    public void addCardToList(Card card){
        lessonPlanList.add(card);
        isSaved = false;
    }

    public void changeSavedState(boolean state) {
        isSaved = state;
    }

    public void editListOfCards(List<Card> newList) {
        lessonPlanList = newList;
    }

    @Override
    public String toString() {
        return title;
    }
}
