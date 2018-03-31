package seedu.address.model.goal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class GoalTextTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GoalText(null));
    }

    @Test
    public void constructor_invalidGoalText_throwsIllegalArgumentException() {
        String invalidGoalText = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GoalText(invalidGoalText));
    }

    @Test
    public void isValidGoalText() {
        // null goal text
        Assert.assertThrows(NullPointerException.class, () -> GoalText.isValidGoalText(null));

        // blank goal text
        assertFalse(GoalText.isValidGoalText("")); // empty string
        assertFalse(GoalText.isValidGoalText(" ")); // spaces only

        // valid goal text
        assertTrue(GoalText.isValidGoalText("1"));
        assertTrue(GoalText.isValidGoalText("aaa0"));  // alphanumerical
        assertTrue(GoalText.isValidGoalText("!@$#()_+"));   // symbols only
        assertTrue(GoalText.isValidGoalText("-1.122ewk:!@|!+@!*~"));   // all kinds of symbols and alphanumerical
        assertTrue(GoalText.isValidGoalText("! 1 wq ")); // with spaces
        assertTrue(GoalText.isValidGoalText("            7"));  // spaces with a value
    }
}
