package edu.augustana.filters;

import edu.augustana.Card;

import java.util.Arrays;
import java.util.List;

public class SearchFilter extends CardFilter{
    public SearchFilter(List<String> searchTerms) {
        super(searchTerms);
    }

    @Override
    public boolean matchesFilters(Card card){
        List<String> wordsToSearchFor = super.getListOfDesiredFilters();

        //I think the problem is in these two methods
        String[] keywords = card.getKeywords();
/*        for (int index = 0; index < keywords.length; index++) {
            if (wordsToSearchFor.contains(keywords[index])) {
                keywordsMatch = true;
            }
        }*/
        String[] equipment = card.getEquipment();
/*        for (int index = 0; index < equipment.length; index++) {
            if (wordsToSearchFor.contains(equipment[index])) {
                equipmentMatch = true;
            }
        }*/
        //Arrays.stream(equipment).toArray();
        for(int i = 0; i < wordsToSearchFor.size(); i++){
            for (int index = 0; index < keywords.length; index++) {
                if (keywords[index].toLowerCase().contains(wordsToSearchFor.get(i).toLowerCase()) || wordsToSearchFor.get(i).toLowerCase().contains(keywords[index].toLowerCase())) {
                    return true;
                }
            }
            for (int index = 0; index < equipment.length; index++) {
                if (equipment[index].toLowerCase().contains(wordsToSearchFor.get(i).toLowerCase()) || wordsToSearchFor.get(i).toLowerCase().contains(equipment[index].toLowerCase())) {
                    return true;
                }
            }
        }
        if (super.checkIfListEmpty(wordsToSearchFor) ||
                wordsToSearchFor.contains(card.getCategory().toLowerCase())||
                wordsToSearchFor.contains(card.getCode().toLowerCase()) ||
                wordsToSearchFor.contains(card.getEvent().toLowerCase()) ||
                wordsToSearchFor.contains(String.valueOf(card.getGender()).toLowerCase()) ||
                wordsToSearchFor.contains(String.valueOf(card.getModelSex()).toLowerCase()) ||
                wordsToSearchFor.contains(card.getTitle())) {
            return true;
        }
        return false;
    }

    /* private static final EventFilter eventFilter = new EventFilter();
    private static final GenderFilter genderFilter = new GenderFilter();
    private static final ModelSexFilter modelSexFilter = new ModelSexFilter();
    private static final LevelFilter levelFilter = new LevelFilter();
    private static final CategoryFilter categoryFilter = new CategoryFilter();
    private static final CodeFilter codeFilter = new CodeFilter();
    private static final EquipmentFilter equipmentFilter = new EquipmentFilter();
    private static final KeywordsFilter keywordsFilter = new KeywordsFilter();
    private static final TitleFilter titleFilter = new TitleFilter();
    
    public boolean match(Card card){
        if(categoryFilter.match(card) || codeFilter.match(card) || equipmentFilter.match(card) ||
                eventFilter.match(card) || genderFilter.match(card) || keywordsFilter.match(card) || levelFilter.match(card) ||
                modelSexFilter.match(card) || titleFilter.match(card)){
            return true;
        }
        return false;
    }
    public void setFilter(String filter){
        categoryFilter.setFilter(filter);
        codeFilter.setFilter(filter);
        equipmentFilter.setFilter(filter);
        eventFilter.setFilter(filter);
        genderFilter.setFilter(filter);
        keywordsFilter.setFilter(filter);
        levelFilter.setFilter(filter);
        modelSexFilter.setFilter(filter);
        titleFilter.setFilter(filter);
    }
    public void resetFilter(){
        categoryFilter.resetFilter();
        codeFilter.resetFilter();
        equipmentFilter.resetFilter();
        eventFilter.resetFilter();
        genderFilter.resetFilter();
        keywordsFilter.resetFilter();
        levelFilter.resetFilter();
        modelSexFilter.resetFilter();
        titleFilter.resetFilter();
    } */
}
