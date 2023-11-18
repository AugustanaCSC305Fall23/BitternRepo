package edu.augustana.Model;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardDatabase {
    private static CardCollection fullCardCollection;

    //https://www.callicoder.com/java-read-write-csv-file-opencsv/
    //https://lovelace.augustana.edu/q2a/index.php/7556/what-is-the-best-way-to-add-new-card-packs-to-the-software
    private static void addCardsFromCSVFile(File csvFile) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).build();
        csvReader.readNext();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            fullCardCollection.addCard(new Card(nextLine));
        }
    }

    // Used https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java/26215931#26215931
    public static void addCardsFromAllCSVFiles() throws IOException, CsvValidationException {
        fullCardCollection = new CardCollection();
        File folder = new File("CardPacks");
        List<File> csvFileList = new ArrayList<>();
        csvFileList = listCSVFilesFromFolder(folder, csvFileList);
        for (File csvFile : csvFileList) {
            addCardsFromCSVFile(csvFile);
        }

    }

    // Used https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java/26215931#26215931
    public static List<File> listCSVFilesFromFolder(File folder, List<File> csvFileList) {
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listCSVFilesFromFolder(fileEntry, csvFileList);
            } else {
                if (fileEntry.getPath().endsWith(".csv")) {
                    csvFileList.add(fileEntry);
                }
            }
        }
        return csvFileList;
    }

    // Used https://lovelace.augustana.edu/q2a/index.php/7241/image-in-javafx
    // Used https://stackoverflow.com/questions/59029879/javafx-image-from-resources-folder
    // Used https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
    public static Image getImageFromPack(String cardPack, String imageFilename) throws MalformedURLException {
        String url = new File("CardPacks/" + cardPack.toUpperCase() + "Pack/" + imageFilename).toURI().toURL().toString();
        //Image image = new Image(url, 402.45, 310.97, true, true);
        return new Image(url);
    }

    public static CardCollection getFullCardCollection() {
        return fullCardCollection;
    }
}
