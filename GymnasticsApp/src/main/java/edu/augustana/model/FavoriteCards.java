package edu.augustana.model;
import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//https://howtodoinjava.com/java/io/java-filereader/
//https://howtodoinjava.com/java/io/java-filewriter/
public class FavoriteCards {
    File favCardsFile = new File("Data/favoriteCards");
    BufferedReader fileReader = new BufferedReader(new FileReader(favCardsFile));
    FileWriter fileWriter = new FileWriter(favCardsFile, true);
    List<Card> favoriteCards = new ArrayList<>();

    public FavoriteCards() throws IOException {
        System.out.println(favCardsFile.toPath().toAbsolutePath().toString());
        /**
         * code from
         * //https://howtodoinjava.com/java/io/java-filereader/
         */
        try {
            String line;
            while ((line = fileReader.readLine()) != null) {
                Card card = CardDatabase.getFullCardCollection().getCardByID(line);
                System.out.println(card.toString());
                favoriteCards.add(card);
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
    }
    public void deleteFavorite(){
        //delete card from file (unique id)
        //delete card from list

    }
    public List<Card> getFavoriteCards(){
        return favoriteCards;
    }
}
