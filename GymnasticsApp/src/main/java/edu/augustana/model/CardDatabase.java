package edu.augustana.model;

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

    /**
     * Gets data from CSV files in all card packs and forms card collection
     *
     * @throws IOException
     * @throws CsvValidationException
     *
     * Used https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java/26215931#26215931
     */
    public static void addCardsFromAllCSVFiles() throws IOException, CsvValidationException {
        fullCardCollection = new CardCollection();
        File folder = new File("cardpacks");
        List<File> csvFileList = new ArrayList<>();
        csvFileList = listCSVFilesFromFolder(folder, csvFileList);
        for (File csvFile : csvFileList) {
            addCardsFromCSVFile(csvFile);
        }
    }

    /**
     * Adds cards to the card collection using data from the CSV file
     * @param csvFile - the CSV file to read data from
     * @throws IOException
     * @throws CsvValidationException
     *
     * Used https://www.callicoder.com/java-read-write-csv-file-opencsv/
     * and https://lovelace.augustana.edu/q2a/index.php/7556/what-is-the-best-way-to-add-new-card-packs-to-the-software
     */
    private static void addCardsFromCSVFile(File csvFile) throws IOException, CsvValidationException {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).build();
        csvReader.readNext();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            fullCardCollection.addCard(new Card(nextLine));
        }
    }

    /**
     * Determines if a file in the folder is a CSV file and if so adds it to a list of all CSV files
     * @param folder - the card pack folder to search through
     * @param csvFileList - the list of CSV files
     * @return the list of CSV files in the folder
     */
    // Used https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java/26215931#26215931
    private static List<File> listCSVFilesFromFolder(File folder, List<File> csvFileList) {
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

    /**
     * Gets the image from the card pack based on the image file name, of type png
     *
     * @param cardPack - the card pack where the image is located
     * @param imageFilename - the image file name
     * @return the image
     * @throws MalformedURLException
     *
     * Used https://lovelace.augustana.edu/q2a/index.php/7241/image-in-javafx
     * and https://stackoverflow.com/questions/59029879/javafx-image-from-resources-folder
     * and https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
     */
    public static Image getImageFromPack(String cardPack, String imageFilename) throws MalformedURLException {
        String url = new File("cardpacks/" + cardPack + "/" + imageFilename + ".png").toURI().toURL().toString();
        return new Image(url);
    }

    /**
     * Gets the thumbnail image from the card pack based on the file name, of type jpg
     *
     * @param cardPack - the card pack where the thumbnail is located
     * @param imageFilename - the name of the thumbnail image
     * @return the thumbnail image
     * @throws MalformedURLException
     */
    public static Image getThumbnail(String cardPack, String imageFilename) throws MalformedURLException {
        String url = new File("cardpacks/" + cardPack + "/thumbs/" + imageFilename + ".jpg").toURI().toURL().toString();
        return new Image(url);
    }

    public static CardCollection getFullCardCollection() {
        return fullCardCollection;
    }
}
