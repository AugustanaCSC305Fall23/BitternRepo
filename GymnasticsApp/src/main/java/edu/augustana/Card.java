package edu.augustana;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    private String code;
    private String event;
    private String category;
    private String title;
    private String packFolder;
    private ImageView image;
    private char gender;
    private char modelSex;
    private String level;
    private String[] equipment;
    private String[] keywords;

    public Card(String cardDataLine) {
        String[] cardDataArray = cardDataLine.split(",");
        code = cardDataArray[0];
        event = cardDataArray[1];
        category = cardDataArray[2];
        title = cardDataArray[3];
        packFolder = cardDataArray[4];
        image = new ImageView(new Image(cardDataArray[5]));
        gender = cardDataArray[6].toCharArray()[0];
        modelSex = cardDataArray[7].toCharArray()[0];
        level = cardDataArray[8];
        equipment = cardDataArray[9].split(",");
        keywords = cardDataArray[10].split(",");
    }
}
