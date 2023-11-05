package edu.augustana;

import edu.augustana.filters.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterControl {
    private static CardFilter eventFilter = new EventFilter(new ArrayList<>());
    private static CardFilter genderFilter = new GenderFilter(new ArrayList<>());
    private static CardFilter levelFilter = new LevelFilter(new ArrayList<>());
    private static CardFilter modelSexFilter = new ModelSexFilter(new ArrayList<>());
    //private static List<CardFilter> listOfFilters = Arrays.asList(eventFilter, genderFilter, levelFilter, modelSexFilter);

    public static List<CardFilter> updateFilterLists(List<String> eventCheckList, List<String> genderCheckList, List<String> levelCheckList, List<String> modelSexCheckList) {
        eventFilter.updateListOfDesiredFilters(eventCheckList);
        genderFilter.updateListOfDesiredFilters(convertListToCharacters(genderCheckList));
        levelFilter.updateListOfDesiredFilters(levelCheckList);
        modelSexFilter.updateListOfDesiredFilters(convertListToCharacters(modelSexCheckList));
        return Arrays.asList(eventFilter, genderFilter, levelFilter, modelSexFilter);
    }

    // Used to change "Male", "Female", and "Neutral" to "M", "F", and "N" so it matches the data in the csv file
    public static List<String> convertListToCharacters(List<String> checkedFilters) {
        List<String> shorterFilterList = new ArrayList<>();
        for (int i = 0; i < checkedFilters.size(); i++) {
            if (checkedFilters.get(i).equals("Boy")) {
                shorterFilterList.add("M");
            } else if (checkedFilters.get(i).equals("Girl")) {
                shorterFilterList.add("F");
            } else {
                shorterFilterList.add("N");
            }
        }
        return checkedFilters;
    }

    public static boolean checkIfAllFiltersMatch(Card cardToCheck) {
        List<CardFilter> cardFilterTypes = Arrays.asList(eventFilter, genderFilter, levelFilter, modelSexFilter);
        for (int i = 0; i < cardFilterTypes.size(); i++) {
            if (!(cardFilterTypes.get(i).matchesFilters(cardToCheck))) {
                return false;
            }
        }
        return true;
    }

    public static void resetDesiredFiltersLists() {
        List<CardFilter> cardFilterTypes = Arrays.asList(eventFilter, genderFilter, levelFilter, modelSexFilter);
        for (CardFilter filter : cardFilterTypes) {
            filter.resetDesiredFiltersList();
        }
    }
}
