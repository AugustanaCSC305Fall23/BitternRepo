package edu.augustana.ui;

import edu.augustana.model.Card;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;

public class CardView extends ImageView {

    private final Card card;
    public CardView(Card card) throws MalformedURLException {
        super(card.getThumbnail());
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
