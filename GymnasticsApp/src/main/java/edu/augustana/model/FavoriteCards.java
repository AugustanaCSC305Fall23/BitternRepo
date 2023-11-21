package edu.augustana.model;
import edu.augustana.ui.CardView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//https://howtodoinjava.com/java/io/java-filereader/
//https://howtodoinjava.com/java/io/java-filewriter/
public class FavoriteCards {
    File favCardsFile = new File("cardpacks/favoriteCards");
    BufferedReader fileReader = new BufferedReader(new FileReader(favCardsFile));
    FileWriter fileWriter = new FileWriter(favCardsFile, true);
    List<Card> favoriteCards = new ArrayList<>();

    List<CardView> favoritesCardView = new ArrayList<>();

    public FavoriteCards() throws IOException {
        /**
         * code from
         * //https://howtodoinjava.com/java/io/java-filereader/
         */
        try {
            String line;
            while ((line = fileReader.readLine()) != null) {
                Card card = CardDatabase.getFullCardCollection().getCardByID(line);
                favoriteCards.add(card);
                favoritesCardView.add(new CardView(card));
            }
        } finally {
            fileReader.close();
        }
    }
    public void addFavorite(Card card) throws IOException {
        //add card to file (unique id)
        //add card to list
        fileWriter.append(card.getUniqueID() + "\n");
        favoriteCards.add(card);
        favoritesCardView.add((new CardView(card)));
    }
    public void deleteFavorite(){
        //delete card from file (unique id)
        //delete card from list

    }
    public void closeFileWriter() throws IOException {
        fileWriter.close();
    }
    public List<Card> getFavoriteCardsList(){
        return favoriteCards;
    }

    public List<CardView> getFavoritesCardView(){return favoritesCardView;}
}
