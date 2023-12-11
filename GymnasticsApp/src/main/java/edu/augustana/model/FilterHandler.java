package edu.augustana.model;

import edu.augustana.filters.*;

import java.util.ArrayList;
import java.util.List;

public class FilterHandler {

    /**
     * Creates a combined filter with all the different types of filters
     * @param searchTermList - the list of terms to search for
     * @param checkedEvents - the list of desired events to filter for
     * @param checkedGenders - the list of desired genders to filter for
     * @param checkedLevels - the list of desired levels to filter for
     * @param checkedModelGenders - the list of desired model genders to filter for
     * @return the combined filter
     */
    public CardFilter getCombinedFilter(List<String> searchTermList, List<String> checkedEvents, List<String> checkedGenders, List<String> checkedLevels, List<String> checkedModelGenders) {
        CardFilter searchFilter = new SearchFilter(searchTermList);
        CardFilter eventFilter = new EventFilter(checkForTramp(checkedEvents));
        CardFilter genderFilter = new GenderFilter(convertGenderWordsToCharacters(checkedGenders));
        CardFilter levelFilter = new LevelFilter(convertLevelWordsToKeys(checkedLevels));
        CardFilter modelSexFilter = new ModelGenderFilter(convertGenderWordsToCharacters(checkedModelGenders));
        return new CombinedAndFilter(searchFilter, eventFilter, genderFilter, levelFilter, modelSexFilter);
    }

    // Checks to see if trampoline is a desired filter
    private List<String> checkForTramp(List<String> checkedEventFilters){
        List<String> matchingCSVEvents = new ArrayList<>();
        for (String checkedEventFilter : checkedEventFilters) {
            if (checkedEventFilter.equals("Trampoline")) {
                matchingCSVEvents.add("Tramp");
            } else matchingCSVEvents.add(checkedEventFilter);
        }
        return matchingCSVEvents;
    }

    // Converts the words Beginner, Advanced Beginner, Intermediate, and Advanced into
    // B, AB, I, and A so that they match the data in the CSV file
    private List<String> convertLevelWordsToKeys(List<String> checkedLevelFilters){
        List<String> matchingLevelKey = new ArrayList<>();
        for (String checkedLevelFilter : checkedLevelFilters) {
            if (checkedLevelFilter.equals("Beginner")) {
                matchingLevelKey.add("B");
            } else if (checkedLevelFilter.equals("Advanced Beginner")) {
                matchingLevelKey.add("AB");
            } else if (checkedLevelFilter.equals("Intermediate")) {
                matchingLevelKey.add("I");
            } else {
                matchingLevelKey.add("A");
            }
        }
        return matchingLevelKey;
    }

    /**
     * Converts the letter abbreviations A, AB, I, and B into their equivalent level words:
     * Advanced, Advanced Beginner, Intermediate, and Beginner, for use when searching
     * @param cardLevels - the String containing the card level abbreviations
     * @return a list of the card levels in words
     */
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
    private List<String> convertGenderWordsToCharacters(List<String> checkedFilters) {
        List<String> shorterFilterList = new ArrayList<>();
        for (String checkedFilter : checkedFilters) {
            if (checkedFilter.equals("Boy")) {
                shorterFilterList.add("M");
            } else if (checkedFilter.equals("Girl")) {
                shorterFilterList.add("F");
            } else {
                shorterFilterList.add("N");
            }
        }
        return shorterFilterList;
    }
}
