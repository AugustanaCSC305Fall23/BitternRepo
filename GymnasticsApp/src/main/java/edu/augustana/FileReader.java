package edu.augustana;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    //https://www.callicoder.com/java-read-write-csv-file-opencsv/
    private static CardCollection cardCollection;

    public static void fillCardCollection() {
        try {
            cardCollection = new CardCollection();
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/edu/augustana/Data/DEMO1.csv"));
            CSVReader csvReader = new CSVReader(reader);
            String[] firstLine = csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                cardCollection.addCard(new Card(nextRecord));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CardCollection getCardCollection() {
        return cardCollection;
    }

    public static List<Image> getImageList() {
        List<Image> imageList = new ArrayList<Image>();
        List<Card> cardList = cardCollection.getCardList();
        for (Card card : cardList) {
            imageList.add(card.getImage());
        }

        return imageList;
    }
}
