package edu.augustana;

import java.util.ArrayList;
import java.util.List;

public class LessonPlan {
    private String title;
    private List<String> cardIDList = new ArrayList<>();

    public LessonPlan(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }
    public List<Card> getCardList(){
        List<Card> cardsInLesson = new ArrayList<>();
        for (String id : cardIDList) {
            cardsInLesson.add(CardDatabase.getFullCardCollection().getCardByID(id));
        }
        return cardsInLesson;
    }

    public void addCardToList(Card card){
        cardIDList.add(card.getUniqueID());
    }

    @Override
    public String toString() {
        return title;
    }
}
