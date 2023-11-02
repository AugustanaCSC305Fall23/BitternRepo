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

public class CardDatabase {
    //https://www.callicoder.com/java-read-write-csv-file-opencsv/
    private static CardCollection fullCardCollection;
    private static List<Image> imageList;

    private static void addCardsFromCSVFile(String fileName) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/edu/augustana/Data/" + fileName));
            CSVReader csvReader = new CSVReader(reader);
            String[] firstLine = csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                fullCardCollection.addCard(new Card(nextRecord));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCardsFromAllCSVFiles() {
        fullCardCollection = new CardCollection();
        addCardsFromCSVFile("DEMO1.csv");
    }

    public static void createImageList() {
        imageList = new ArrayList<Image>();
        List<Card> cardList = fullCardCollection.getCardList();
        for (Card card : cardList) {
            imageList.add(card.getImage());
        }
    }

    public static CardCollection getFullCardCollection() {
        return fullCardCollection;
    }

    public static List<Image> getImageList() {
        return imageList;
    }
}
