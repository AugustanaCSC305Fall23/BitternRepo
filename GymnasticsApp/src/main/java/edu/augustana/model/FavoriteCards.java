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
        FileWriter fileWriter = new FileWriter(favCardsFile, true);
        if(!favoriteCards.contains(card)){
            fileWriter.append(card.getUniqueID() + "\n");
            favoriteCards.add(card);
            favoritesCardView.add((new CardView(card)));
        }
        fileWriter.close();
    }
    public void deleteFavorite(Card card) throws IOException {
        if(favoriteCards.contains(card)){
            favoriteCards.remove(card);
        }
        reWriteFavoritesFile();
    }

    public void removeFavoriteCardView(CardView card){
        favoritesCardView.remove(card);
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
