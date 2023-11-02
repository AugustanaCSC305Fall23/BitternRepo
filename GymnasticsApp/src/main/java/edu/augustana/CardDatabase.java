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
import java.util.Map;

public class CardDatabase {
    private static CardCollection fullCardCollection;

    //https://www.callicoder.com/java-read-write-csv-file-opencsv/
    private static void addCardsFromCSVFile(String fileName) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/edu/augustana/Data/" + fileName));
            CSVReader csvReader = new CSVReader(reader);
            csvReader.readNext();
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

    public static List<Image> getListOfImages() {
        List<Image> imageList = new ArrayList<Image>();
        for (String cardId : getFullCardCollection().getSetOfCardIds()) {
            imageList.add(fullCardCollection.getCard(cardId).getImage());
        }
        return imageList;
    }

    public static CardCollection getFullCardCollection() {
        return fullCardCollection;
    }
}
