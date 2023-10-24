package edu.augustana;

public class GymnasticsEvent {
    private CardCollection cardsInEvent;
    private String eventTitle;


    public void addCard(Card card) {

    }
    public String getEventTitle() {
        return eventTitle;
    }

    public CardCollection getCardsInEvent() {
        return cardsInEvent;
    }



    public void removeCard(Card card) {

    }
    public void changeTitle(String newTitle) {
        eventTitle = newTitle;
    }
    @Override
    public String toString() {
        return "GymnasticsEvent{}";
    }
}
