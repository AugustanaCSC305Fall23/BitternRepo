package edu.augustana.structures;

import edu.augustana.*;
import edu.augustana.model.*;

import java.util.*;

public class IndexedMap{
    private List<Category> indexedMap = new ArrayList<>();

    public IndexedMap(){

    }
    public IndexedMap(Category category){
        indexedMap.add(category);
    }
    public int size(){
        return 0;
    }
    public boolean isEmpty(){
        return indexedMap.isEmpty();
    }

    public Boolean add(Category category) {
        return indexedMap.add(category);
    }

    public void add(String eventHeading, String cardID){
        for(int i = 0; i < indexedMap.size(); i++){
            if(indexedMap.get(i).getCategoryHeading().equalsIgnoreCase(eventHeading)){
                indexedMap.get(i).addCardToList(cardID);
            }
        }
    }
/*    public void add(String eventHeading, List<String> IDList){
        for(int i = 0; i < indexedMap.size(); i++){
            if(indexedMap.get(i).getCategoryHeading().equalsIgnoreCase(eventHeading)){
                indexedMap.get(i).addCardToList(cardID);
            }
        }
    }*/

    public boolean remove(Category o) {
        return indexedMap.remove(o);
    }

    public void clear() {
        indexedMap.clear();
    }

    public Category get(int index) {
        return indexedMap.get(index);
    }
    public Boolean contains(String heading){
        for(int i = 0; i < indexedMap.size(); i++){
            if(indexedMap.get(i).getCategoryHeading().equalsIgnoreCase(heading)){
                return true;
            }
        }
        return false;
    }
    public int get(String category){
        //TODO:
        for(int i = 0; i < indexedMap.size(); i++){
            if(indexedMap.get(i).getCategoryHeading().equals(category)){
                return i;
            }
        }
        return -1;
    }

    public Category set(int index, Category element) {
        return indexedMap.set(index, element);
    }

    public void add(int index, Category element) {
        indexedMap.add(index, element);
    }

    public int indexOf(Category category) {
        for(int i = 0; i < indexedMap.size(); i++){
            if(indexedMap.get(i).equals(category)){
                return i;
            }
        }
        return -1;
    }

    public ListIterator<Category> listIterator() {
        return indexedMap.listIterator();
    }

    public ListIterator<Category> listIterator(int index) {
        return indexedMap.listIterator(index);
    }

    public List<Category> subList(int fromIndex, int toIndex) {
        return indexedMap.subList(fromIndex, toIndex);
    }
    public void move(int fromIndex, int toIndex){
        //changes the position in the list that the heading is in along with
        //moving everything before or after
    }
    public void moveByOne(int direction, int index){
        //moves by one index for each call to method
        Category temp = indexedMap.get(direction + index);
        indexedMap.set(direction + index, indexedMap.get(index));
        indexedMap.set(index, temp);
    }
    @Override
    public String toString(){
        String toReturn;
        String header;
        String list;
        toReturn = "{";
        for(Category category : indexedMap){
            header = category.getCategoryHeading();
            list = category.getCardsInList().toString();
            toReturn = toReturn + header + " : " + list + "\n";
        }
        return toReturn + "}\n";
    }

}
