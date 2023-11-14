package edu.augustana;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;
    private static List<Card> lessonPlanList = new ArrayList<>();
    private boolean isSaved;
    private Map<String, List<String>> eventInPlanList = new TreeMap<>();

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

    public void addEventToPlanList(Card card){
        List<String> cardDisplay = new ArrayList<>();
        cardDisplay.add(card.getCode() + ", " + card.getTitle());
        eventInPlanList.put(card.getEvent(), cardDisplay);
    }
    //rename this method
    public void addCardToEvent(Card card){
        eventInPlanList.get(card.getEvent()).add(card.getCode() + ", " + card.getTitle());
    }
    public boolean eventInPlanList(Card card){
        if(eventInPlanList.containsKey(card.getEvent())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return title;
    }
}
