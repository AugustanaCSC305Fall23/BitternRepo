package edu.augustana.filters;

import edu.augustana.Card;

public class SearchFilter implements CardFilter{
    private static final EventFilter eventFilter = new EventFilter();
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
    }
}
