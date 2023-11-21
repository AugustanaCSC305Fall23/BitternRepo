package edu.augustana;
import static org.junit.jupiter.api.Assertions.*;

import com.opencsv.exceptions.CsvValidationException;
import edu.augustana.model.Card;
import edu.augustana.model.CardDatabase;
import edu.augustana.model.Course;
import edu.augustana.model.LessonPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CourseUnitTest {
    //CardDatabase cardDatabase = new CardDatabase();
    Course course = new Course();

    @Test
    public void testAddNewLessonPlan() {
        course.createNewLessonPlan();
        assertEquals("[My Lesson Plan]", course.getLessonPlanList().toString());
        course.getLessonPlanList().get(0).setTitle("Team Bittern!");
        course.createNewLessonPlan();
        assertEquals("[Team Bittern!, My Lesson Plan]", course.getLessonPlanList().toString());
    }
    @Test
    public void testChangeTitle(){
        course.changeTitle("Test Test");
        assertEquals("Test Test", course.getTitle());
    }
}
