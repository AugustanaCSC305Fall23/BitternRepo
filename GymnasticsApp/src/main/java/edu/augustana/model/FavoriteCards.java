package edu.augustana.model;
import edu.augustana.ui.CardView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

//https://howtodoinjava.com/java/io/java-filereader/
//https://howtodoinjava.com/java/io/java-filewriter/
public class FavoriteCards {
    File favCardsFile = new File("cardpacks/favoriteCards");
    BufferedReader fileReader = new BufferedReader(new FileReader(favCardsFile));
    List<Card> favoriteCards = new ArrayList<>();

    List<CardView> favoritesCardView = new ArrayList<>();

    /**
     * Adds the cards and cardViews from the uniqueID's listed in the favoriteCards
     * file to their respective lists
     * @throws IOException if favoriteCards file is not found
     */
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

    /**
     * adds the card's uniqueID to the favoriteCards file
     * and creates a cardView of the card
     * @param card to be added to list of favorite cards
     * @throws IOException if the favoriteCards file is not found
     */
    public void addFavorite(Card card) throws IOException {
        FileWriter fileWriter = new FileWriter(favCardsFile, true);
        if(!favoriteCards.contains(card)){
            fileWriter.append(card.getUniqueID() + "\n");
            favoriteCards.add(card);
            favoritesCardView.add((new CardView(card)));
        }
        fileWriter.close();
    }

    /**
     * Removes a card from the list of favorite cards and rewrites
     * the file with the updated list of favorite cards
     * @param card Card being removed
     * @throws IOException if favoriteCards file is not found
     */
    public void deleteFavorite(Card card) throws IOException {
        if (favoriteCards.contains(card)){
            favoriteCards.remove(card);
        }
        reWriteFavoritesFile();
    }

    /**
     * Removes the CardView from the list of CardViews of cards
     * that have been favorited
     * @param cardView CardView to be removed
     */
    public void removeFavoriteCardView(CardView cardView){
        favoritesCardView.remove(cardView);
    }

    private void reWriteFavoritesFile() throws IOException {
        FileWriter fileWriter = new FileWriter(favCardsFile, false);
        fileWriter.flush();
        fileWriter.close();
        FileWriter reWriter = new FileWriter(favCardsFile, true);
        for(Card cardInList : favoriteCards){
            reWriter.append(cardInList.getUniqueID() + "\n");
        }
        reWriter.close();
    }

    public List<Card> getFavoriteCardsList(){
        return favoriteCards;
    }

    public List<CardView> getFavoritesCardView(){return favoritesCardView;}
}
