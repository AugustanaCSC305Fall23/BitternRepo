package edu.augustana.model;

import edu.augustana.filters.*;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterHandler {
    public CardFilter getCombinedFilter(List<String> searchTermList, List<String> checkedEvents, List<String> checkedGenders, List<String> checkedLevels, List<String> checkedModelSexes) {
        CardFilter searchFilter = new SearchFilter(searchTermList);
        CardFilter eventFilter = new EventFilter(checkForTramp(checkedEvents));
        CardFilter genderFilter = new GenderFilter(convertListToCharacters(checkedGenders));
        CardFilter levelFilter = new LevelFilter(changeToLevelKey(checkedLevels));
        CardFilter modelSexFilter = new ModelSexFilter(convertListToCharacters(checkedModelSexes));
        return new CombinedAndFilter(searchFilter, eventFilter, genderFilter, levelFilter, modelSexFilter);
    }

    public static List<String> checkForTramp(List<String> checkedEventFilters){
        List<String> matchingCSVEvents = new ArrayList<>();
        for(int i = 0; i < checkedEventFilters.size(); i++){
            if(checkedEventFilters.get(i).equals("Trampoline")){
                matchingCSVEvents.add("Tramp");
            }else matchingCSVEvents.add(checkedEventFilters.get(i));
        }
        return matchingCSVEvents;
    }
    public static List<String> changeToLevelKey(List<String> checkedLevelFilters){
        List<String> matchingLevelKey = new ArrayList<>();
        for(int i = 0; i < checkedLevelFilters.size(); i++){
            if(checkedLevelFilters.get(i).equals("Beginner")){
                matchingLevelKey.add("B");
            }else if(checkedLevelFilters.get(i).equals("Advanced Beginner")){
                matchingLevelKey.add("AB");
            }else if(checkedLevelFilters.get(i).equals("Intermediate")){
                matchingLevelKey.add("I");
            }else{
                matchingLevelKey.add("A");
            }
        }
        return matchingLevelKey;
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
        return shorterFilterList;
    }
}
