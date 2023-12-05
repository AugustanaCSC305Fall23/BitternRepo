package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;
import edu.augustana.model.LessonPlan;
import edu.augustana.structures.Category;
import javafx.scene.control.TreeItem;

import java.util.ListIterator;

/*
    I used https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm and
    https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm to help with the tree view
    thoughout this class
     */

public class TreeViewManager {
    private LessonPlan lessonPlan;
    public TreeViewManager(LessonPlan lessonPlan){
        this.lessonPlan = lessonPlan;
    }

    /**
     * Sets up the TreeView of a lesson plan
     * @param root the root of the treeView
     * @return the root with the lesson plan built on it
     */
    public TreeItem<String> setUpTreeView(TreeItem<String> root){
        displayLessonPlan(root);
        return root;
    }

    /**
     * @param root of the treeView to add items on to
     * Adds a new TreeItem for each heading in the lesson plan
     *             and adds a TreeItem for each cards in the event under
     *             the TreeItem where the heading is being stored
     */
    private void displayLessonPlan(TreeItem<String> root){
        if(!lessonPlan.isLessonPlanEmpty()){
            Card card;
            for (ListIterator<Category> it = lessonPlan.getLessonPlan().listIterator(); it.hasNext();) {
                Category event = it.next();
                TreeItem<String> newEvent = new TreeItem<>(event.getCategoryHeading());
                for(String cardID : event.getCardsInList()){
                    card = CardDatabase.getFullCardCollection().getCardByID(cardID);
                    newEvent.getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
                }
                root.getChildren().add(newEvent);
            }
        }
    }

    /**
     * Adds a card to the TreeView unless it is already there
     * and the event of the card if it is not already there
     * @param card to be displayed in the TreeView
     * @param root of the TreeView
     * @return the root after the changes have been made
     */
    public TreeItem<String> addToTreeView(Card card, TreeItem<String> root){
        if (!App.getCurrentLessonPlan().eventInPlanList(card)){
            App.getCurrentLessonPlan().addEventToPlanList(card);
            TreeItem<String> newEvent = new TreeItem<>(card.getEvent());
            newEvent.getChildren().add(new TreeItem<>(card.getCode() + ", " + card.getTitle()));
            root.getChildren().add(newEvent);
        } else{
            if (!App.getCurrentLessonPlan().cardInPlanList(card)){
                App.getCurrentLessonPlan().addCardToEvent(card);
                root.getChildren().get(App.getCurrentLessonPlan().getLessonPlan().get(card.getEvent())).getChildren().add(new TreeItem<String>(card.getCode() + ", " + card.getTitle()));
            }
        }
        return root;
    }

    //this method might not be necessary
    public TreeItem<String> removeFromTreeView(TreeItem<String> root){
        root.getChildren().clear();
        displayLessonPlan(root);
        return root;
    }
}
