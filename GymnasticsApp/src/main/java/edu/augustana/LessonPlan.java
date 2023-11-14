package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private List<String> cardIDList = new ArrayList<>();
    private boolean isSaved;

    public LessonPlan(String title) {
        this.title = title;
        isSaved = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
        isSaved = false;
    }
    public List<Card> getCardList(){
        List<Card> cardsInLesson = new ArrayList<>();
        for (String id : cardIDList) {
            cardsInLesson.add(CardDatabase.getFullCardCollection().getCardByID(id));
        }
        return cardsInLesson;
    }

    public boolean getIsSaved() { return isSaved; }

    public void addCardToList(Card card){
        cardIDList.add(card.getUniqueID());
        isSaved = false;
    }

    public void changeSavedState(boolean state) {
        isSaved = state;
    }


    @Override
    public String toString() {
        return title;
    }
}
