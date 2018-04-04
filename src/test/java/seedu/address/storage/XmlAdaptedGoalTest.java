package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedGoal.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalGoals.GOAL_B;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;
import seedu.address.testutil.Assert;

//@@author deborahlow97
public class XmlAdaptedGoalTest {
    private static final String INVALID_IMPORTANCE = "11";
    private static final String INVALID_GOAL_TEXT = " ";

    private static final String VALID_IMPORTANCE = GOAL_B.getImportance().toString();
    private static final String VALID_GOAL_TEXT = GOAL_B.getGoalText().toString();
    private static final String VALID_START_DATE_TIME = GOAL_B.getStartDateTime().toString();
    private static final String VALID_COMPLETION = GOAL_B.getCompletion().toString();
    private static final String VALID_END_DATE_TIME = GOAL_B.getEndDateTime().toString();

    @Test
    public void toModelType_validGoalDetails_returnsGoal() throws Exception {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(GOAL_B);
        assertEquals(GOAL_B, goal.toModelType());
    }

    @Test
    public void toModelType_invalidImportance_throwsIllegalValueException() {
        XmlAdaptedGoal goal =
                new XmlAdaptedGoal(INVALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_COMPLETION);
        String expectedMessage = Importance.MESSAGE_IMPORTANCE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullImportance_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(null, VALID_GOAL_TEXT, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Importance.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_invalidGoalText_throwsIllegalValueException() {
        XmlAdaptedGoal goal =
                new XmlAdaptedGoal(VALID_IMPORTANCE, INVALID_GOAL_TEXT, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_COMPLETION);
        String expectedMessage = GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullGoalText_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, null, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GoalText.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }


    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, null, VALID_END_DATE_TIME,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullCompletion_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Completion.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME, null,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }
}
