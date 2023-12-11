package edu.augustana.model;

import edu.augustana.filters.*;

import java.util.ArrayList;
import java.util.List;

public class FilterHandler {
    public CardFilter getCombinedFilter(List<String> searchTermList, List<String> checkedEvents, List<String> checkedGenders, List<String> checkedLevels, List<String> checkedModelSexes) {
        CardFilter searchFilter = new SearchFilter(searchTermList);
        CardFilter eventFilter = new EventFilter(checkForTramp(checkedEvents));
        CardFilter genderFilter = new GenderFilter(convertGenderWordsToCharacters(checkedGenders));
        CardFilter levelFilter = new LevelFilter(convertLevelWordsToKeys(checkedLevels));
        CardFilter modelSexFilter = new ModelGenderFilter(convertGenderWordsToCharacters(checkedModelSexes));
        return new CombinedAndFilter(searchFilter, eventFilter, genderFilter, levelFilter, modelSexFilter);
    }

    public List<String> checkForTramp(List<String> checkedEventFilters){
        List<String> matchingCSVEvents = new ArrayList<>();
        for(int i = 0; i < checkedEventFilters.size(); i++){
            if(checkedEventFilters.get(i).equals("Trampoline")){
                matchingCSVEvents.add("Tramp");
            }else matchingCSVEvents.add(checkedEventFilters.get(i));
        }
        return matchingCSVEvents;
    }
    public List<String> convertLevelWordsToKeys(List<String> checkedLevelFilters){
        List<String> matchingLevelKey = new ArrayList<>();
        for (int i = 0; i < checkedLevelFilters.size(); i++){
            if (checkedLevelFilters.get(i).equals("Beginner")){
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

    public List<String> convertLevelKeysToWords(String cardLevels) {
        String[] cardLevelSymbols;
        if (cardLevels.contains(",")) {
            cardLevelSymbols = cardLevels.split(",");
        } else {
            cardLevelSymbols = cardLevels.split("\\+s");
        }
        List<String> cardLevelWords = new ArrayList<>();
        for (String level : cardLevelSymbols) {
            level = level.strip();
            if (level.equalsIgnoreCase("ALL")){
                cardLevelWords.add("all");
            } else if (level.equalsIgnoreCase("AB")) {
                cardLevelWords.add("advanced beginner");
            } else if (level.equalsIgnoreCase("A")) {
                cardLevelWords.add("advanced");
            } else if (level.equalsIgnoreCase("I")) {
                cardLevelWords.add("intermediate");
            } else if (level.equalsIgnoreCase("B")) {
                cardLevelWords.add("beginner");
            }
        }

        return cardLevelWords;
    }

    // Used to change "Male", "Female", and "Neutral" to "M", "F", and "N" so it matches the data in the csv file
    public List<String> convertGenderWordsToCharacters(List<String> checkedFilters) {
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
