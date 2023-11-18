package edu.augustana.model;

public class GymnasticsEvent {
    private CardCollection cardsInEvent;
    private String eventTitle;

    public GymnasticsEvent(CardCollection cardsInEvent, String eventTitle) {
        this.cardsInEvent = cardsInEvent;
        this.eventTitle = eventTitle;
    }

    public void addCard(Card card) {
        cardsInEvent.addCard(card);
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
        return "GymnasticsEvent{" +
                "cardsInEvent=" + cardsInEvent +
                ", eventTitle='" + eventTitle + '\'' +
                '}';
    }
}
