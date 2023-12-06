package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;
import edu.augustana.model.LessonPlan;
import edu.augustana.structures.Category;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

import java.util.ListIterator;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

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
     * Sets up the TreeView of a lesson plan by adding a new TreeItem for each category
     * and a new TreeItem for each card under that category
     * @param root the root of the treeView
     * @return the root with the lesson plan built on it
     */
    public TreeItem<String> setUpTreeView(TreeItem<String> root){
        displayLessonPlan(root);
        return root;
    }

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
            expandTreeItem(root);
        }
    }

    private void expandTreeItem(TreeItem<String> root){
        for(TreeItem<String> heading : root.getChildren()){
            heading.setExpanded(true);
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
        expandTreeItem(root);
        return root;
    }

    /**
     * Clears and constructs the TreeView based on the lesson plan
     * @param root of the TreeView
     * @return TreeItem that is the updated root
     */
    public TreeItem<String> removeFromTreeView(TreeItem<String> root){
        root.getChildren().clear();
        displayLessonPlan(root);
        return root;
    }

    /**
     * Changes the heading displayed in the TreeView to what the user entered and makes that change in the
     * lesson plan itself
     * @param newHeading String of the user-entered heading
     * @param headingToEdit Category containing the heading to be changed
     * @param root of the treeview
     * @return the root containing the updates
     */
    public TreeItem<String> setHeadingInTreeView(String newHeading, Category headingToEdit, TreeItem<String> root){
        headingToEdit.setCategoryHeading(newHeading);
        removeFromTreeView(root);
        return root;
    }

    /**
     * Adds changes the order of the categories from the controller
     * in the controller and the lesson plan's indexed map
     * @param selectedEvent Category to be moved
     * @param direction int to move the event (-1 for up and 1 for down)
     * @param root of tree view
     * @return the updated root
     */
    public TreeItem<String> moveEvent(Category selectedEvent, int direction, TreeItem<String> root){
        int index = lessonPlan.getLessonPlan().get(selectedEvent.getCategoryHeading());
        lessonPlan.getLessonPlan().moveByOne(direction, index);
        removeFromTreeView(root);
        return root;
    }

}
