package edu.augustana.model;

import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * Represents a card.
 */
public class Card {

    private String code;
    private String event;
    private String category;
    private String title;
    private String packFolder;
    private String imageName;
    private String gender;
    private String modelSex;
    private String level;
    private String[] equipment;
    private String[] keywords;
    private String uniqueID;
    private Image image;
    private Image thumbnail;
    private String displayedTitle;

    /**
     * Creates a card with the data contained in the passed-in array
     * @param cardDataArray The array containing the data to assign to each field
     * @throws MalformedURLException If the URL for the getThumbnail method is malformed
     */
    public Card(String[] cardDataArray) throws MalformedURLException {
        code = cardDataArray[0].strip();
        event = cardDataArray[1].strip();
        category = cardDataArray[2].strip();
        title = cardDataArray[3].strip();
        packFolder = cardDataArray[4].strip();

        imageName = cardDataArray[5].strip();
        int suffixStartIndex = imageName.indexOf('.');
        imageName = imageName.substring(0, suffixStartIndex);

        gender = cardDataArray[6].strip();
        modelSex = cardDataArray[7].strip();
        level = cardDataArray[8].strip();
        equipment = cardDataArray[9].split(",");
        keywords = cardDataArray[10].split(",");
        uniqueID = packFolder + "/" + imageName;
        thumbnail = CardDatabase.getThumbnail(packFolder, imageName);
        displayedTitle = code + ", " + title;
    }

    public String getCode() {
        return code;
    }

    public String getEvent() {
        return event;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }

    public String getModelSex() {
        return modelSex;
    }

    public String getLevel() {
        return level;
    }

    public String[] getEquipment() {
        return equipment;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public Image getImage() throws MalformedURLException {
        return CardDatabase.getImageFromPack(packFolder, imageName);
    }
    public String getDisplayedTitle() { return displayedTitle;}

    @Override
    public String toString() {
        return "Card{" +
                "code='" + code + '\'' +
                ", event='" + event + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", packFolder='" + packFolder + '\'' +
                ", imageName='" + imageName + '\'' +
                ", gender='" + gender + '\'' +
                ", modelSex='" + modelSex + '\'' +
                ", level='" + level + '\'' +
                ", equipment=" + Arrays.toString(equipment) +
                ", keywords=" + Arrays.toString(keywords) +
                ", uniqueID='" + uniqueID + '\'' +
                ", image=" + image +
                ", thumbnail=" + thumbnail +
                ", displayedTitle='" + displayedTitle + '\'' +
                '}';
    }
}


