package edu.augustana.ui;

import edu.augustana.model.Card;
import javafx.scene.image.ImageView;

public class CardView extends ImageView {

    private final Card card;
    public CardView(Card card) {
        super(card.getImage());
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
