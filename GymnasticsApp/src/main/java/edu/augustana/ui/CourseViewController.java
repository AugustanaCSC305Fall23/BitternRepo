package edu.augustana.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.augustana.App;
import edu.augustana.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class CourseViewController {

    @FXML private ListView<LessonPlan> courseListView = new ListView<>();
    @FXML private VBox upArrow;
    @FXML private VBox downArrow;

    @FXML private HBox buttonBar;
    @FXML private Button createNewLessonPlanBtn;
    @FXML private TextField courseTitleField;
    @FXML private Menu recentFilesMenu;

    @FXML private TreeView<String> lessonPlanTreeView;
    private TreeItem<String> root = new TreeItem<>();
    private TreeViewManager treeViewManager;

    // Non FXML
   // private static Course currentCourse;
    private CourseModel courseModel;
    private TitleEditor titleEditor;
    private List<Button> buttonsList = new ArrayList<>();
    private UndoRedoHandler undoRedoHandler;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
//        if (currentCourse == null) {
//            currentCourse = new Course();
//        }
        courseListView.setOnMouseClicked(e -> checkIfItemSelected());
        courseModel = new CourseModel();
        addLessonsToCourseList();
        undoRedoHandler = new UndoRedoHandler(App.getCurrentCourse());
        titleEditor = new TitleEditor(courseTitleField, new Font("Britannic Bold", 45.0), new Font("Britannic Bold", 45.0), 'C');
        titleEditor.initializeTitleFieldEvents(undoRedoHandler, App.getCurrentCourse().clone());
        titleEditor.setTitleFieldText();
        setUpRecentFilesMenu();
        for (Node node : buttonBar.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                if (btn != createNewLessonPlanBtn) {
                    buttonsList.add(btn);
                }
            }
        }
    }

    private void displayTreeView(LessonPlan lessonPlan){
        //https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
        //To help with tree view
        root = new TreeItem<>(lessonPlan.getTitle());
        lessonPlanTreeView.setRoot(root);
        lessonPlanTreeView.setShowRoot(false);
        treeViewManager = new TreeViewManager(lessonPlan);
        treeViewManager.setUpTreeView(root);
        lessonPlanTreeView.setVisible(true);
    }

    @FXML private void goToHome() throws IOException {
        App.setRoot("home");
    }

    private void checkIfItemSelected() {
        if (!courseListView.getSelectionModel().isEmpty()) {
            disableButtons(false);
            LessonPlan selectedLesson = courseListView.getSelectionModel().getSelectedItem();
            courseModel.setSelectedLessonPlan(selectedLesson);
            displayTreeView(selectedLesson);
        } else {
            disableButtons(true);
            courseModel.setSelectedLessonPlan(null);
            lessonPlanTreeView.setVisible(false);
        }
    }

    private void disableButtons(boolean disable) {
        for (Button btn : buttonsList) {
            btn.setDisable(disable);
        }
    }

    @FXML void deselectLessonPlan() {
        courseModel.setSelectedLessonPlan(null);
        courseListView.getSelectionModel().clearSelection();
        lessonPlanTreeView.setVisible(false);
        disableButtons(true);
    }

    @FXML private void createNewCourseHandler() {
        courseModel.createNewCourse();
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
    }

    @FXML private void openCourseHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        if (courseModel.openCourseFromFiles(mainWindow)) {
            courseTitleField.setText(App.getCurrentCourse().getTitle());
            courseListView.getItems().clear();
            for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                courseListView.getItems().add(lessonPlan);
            }
            App.getRecentFilesManager().addRecentFile(App.getCurrentCourseFile().getPath());
            setUpRecentFilesMenu();
        }
    }

    @FXML private void saveCourseHandler() {
        try {
            courseModel.saveCourse();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
    }

    @FXML private void saveCourseAsHandler() {
        Window mainWindow = courseListView.getScene().getWindow();
        try {
            courseModel.saveCourseAs(mainWindow);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No course file is open").show();
        }
        App.getRecentFilesManager().addRecentFile(App.getCurrentCourseFile().getPath());
        setUpRecentFilesMenu();
    }

    @FXML private void createLessonPlanHandler() throws IOException {
        LessonPlan lessonPlan = App.getCurrentCourse().createNewLessonPlan();
        App.setCurrentLessonPlan(lessonPlan);
        App.setRoot("lesson_plan_creator");
    }

    @FXML private void openRecentFileHandler(String filePath) throws IOException {
        courseModel.openRecentFile(filePath);
        courseTitleField.setText(App.getCurrentCourse().getTitle());
        courseListView.getItems().clear();
        for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
            courseListView.getItems().add(lessonPlan);
        }
        App.getRecentFilesManager().addRecentFile(App.getCurrentCourseFile().getPath());
        setUpRecentFilesMenu();
    }

    @FXML private void exitHandler() {
        Platform.exit();
    }

    @FXML private void setUpRecentFilesMenu() {
        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/MenuItem.html
        //https://stackoverflow.com/questions/57074185/how-to-use-setonaction-event-on-javafx
        recentFilesMenu.getItems().clear();
        for (int i = 0; i < RecentFilesManager.getMaxRecentFiles(); i++) {
            String recentFileNum = Integer.toString(i + 1);
            if (!RecentFilesManager.getUserPreferences().get(recentFileNum, "empty").equals("empty")) {
                MenuItem recentFile = new MenuItem(RecentFilesManager.getUserPreferences().get(recentFileNum, "empty"));
                recentFile.setOnAction(event -> {
                    try {
                        openRecentFileHandler(recentFile.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                recentFilesMenu.getItems().add(recentFile);
            }
        }
    }

    @FXML private void editLessonPlanHandler() throws  IOException {
        LessonPlan lessonPlanToEdit = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToEdit != null) {
            App.setCurrentLessonPlan(lessonPlanToEdit);
            App.setRoot("lesson_plan_creator");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Lesson Plan first.");
        }
    }

    @FXML private void removeLessonPlanHandler() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to delete this lesson plan?");
        Optional<ButtonType> confirmation =  alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            LessonPlan lessonPlanToDelete = courseListView.getSelectionModel().getSelectedItem();
            if (lessonPlanToDelete != null) {
                courseListView.getItems().remove(lessonPlanToDelete);
                App.getCurrentCourse().getLessonPlanList().remove(lessonPlanToDelete);
                App.setCurrentLessonPlan(null);
                undoRedoHandler.saveState(App.getCurrentCourse().clone());
            }
        }

    }

    @FXML
    private void addLessonsToCourseList() {
        List<LessonPlan> lessonPlanList = App.getCurrentCourse().getLessonPlanList();
        if (!(lessonPlanList.isEmpty())) {
            for (LessonPlan lesson: lessonPlanList) {
                courseListView.getItems().add(lesson);
            }
        }
    }

    @FXML private void duplicateLessonPlanHandler() {
        LessonPlan lessonPlanToDuplicate = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToDuplicate != null) {
            LessonPlan copyOfLessonPlan = new LessonPlan();
            copyOfLessonPlan.setTitle(lessonPlanToDuplicate.getTitle());
            copyOfLessonPlan.setEventInPlanList(lessonPlanToDuplicate.getLessonPlanIndexedMap());
            App.getCurrentCourse().getLessonPlanList().add(lessonPlanToDuplicate);
            courseListView.getItems().add(copyOfLessonPlan);
            undoRedoHandler.saveState(App.getCurrentCourse());
        }
    }

    @FXML public void printLessonPlanHandler(ActionEvent actionEvent) throws IOException {
        LessonPlan lessonPlanToDuplicate = courseListView.getSelectionModel().getSelectedItem();
        if (lessonPlanToDuplicate != null) {
            Map<String, List<Card>> eventToCardMap = lessonPlanToDuplicate.getMapOfCardsFromID(lessonPlanToDuplicate.getLessonPlanIndexedMap());
            String lessonPlanTitle = lessonPlanToDuplicate.getTitle();

            boolean cardDisplay;
            boolean landscapeDisplay = false;

            // If true, show cards. Else, show text only
            cardDisplay = PrintStaging.promptCardDisplay();

            // If true shows landscape mode. Else, show portrait mode
            if (cardDisplay) {
                landscapeDisplay = PrintStaging.promptPageFormat();
            }

            boolean equipmentDisplay = PrintStaging.promptForEquipment();

            new PrintStaging(lessonPlanTitle, eventToCardMap, "course_view", cardDisplay, landscapeDisplay, equipmentDisplay);
            App.setRoot("print_preview");
        }
    }

    @FXML
    public void undo() {
        undoRedoHandler.undo(App.getCurrentCourse());
        titleEditor.setTitleFieldText();
        courseListView.getItems().clear();
        addLessonsToCourseList();

    }

    @FXML
    public void redo() {
        undoRedoHandler.redo(App.getCurrentCourse());
        titleEditor.setTitleFieldText();
        courseListView.getItems().clear();
        addLessonsToCourseList();
    }

}