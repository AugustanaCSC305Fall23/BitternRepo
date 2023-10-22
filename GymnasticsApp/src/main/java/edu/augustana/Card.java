package edu.augustana;

import javafx.scene.image.Image;

public class Card {
    private String code;
    private String event;
    private String category;
    private String title;
    private String packFolder;
    private String imageName;
    private Image image;
    private char gender;
    private char modelSex;
    private String level;
    private String[] equipment;
    private String[] keywords;

    public Card(String[] cardDataArray) {
        //String[] cardDataArray = cardDataLine.split(",");
        code = cardDataArray[0].strip();
        event = cardDataArray[1].strip();
        category = cardDataArray[2].strip();
        title = cardDataArray[3].strip();
        packFolder = cardDataArray[4].strip();
        imageName = cardDataArray[5].strip();
        // Used https://lovelace.augustana.edu/q2a/index.php/7241/image-in-javafx
        // Used https://stackoverflow.com/questions/59029879/javafx-image-from-resources-folder
        // Used https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
        image = new Image((getClass().getResource("images/1.png")).toString(), 400, 300, true, true);
        gender = cardDataArray[6].strip().toCharArray()[0];
        modelSex = cardDataArray[7].strip().toCharArray()[0];
        level = cardDataArray[8].strip();
        equipment = cardDataArray[9].split(",");
        keywords = cardDataArray[10].split(",");
    }

    public Image getImage() {
        return image;
    }
}