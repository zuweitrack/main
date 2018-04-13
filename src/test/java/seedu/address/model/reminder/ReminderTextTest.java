package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author fuadsahmawi
public class ReminderTextTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ReminderText(null));
    }

    @Test
    public void constructor_invalidReminderText_throwsIllegalArgumentException() {
        String invalidReminderText = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ReminderText(invalidReminderText));
    }

    @Test
    public void isValidGoalText() {
        // null reminder text
        Assert.assertThrows(NullPointerException.class, () -> ReminderText.isValidReminderText(null));

        // blank reminder text
        assertFalse(ReminderText.isValidReminderText("")); // empty string
        assertFalse(ReminderText.isValidReminderText(" ")); // spaces only

        // valid reminder text
        assertTrue(ReminderText.isValidReminderText("1"));
        assertTrue(ReminderText.isValidReminderText("aaa0")); // alphanumerical
        assertTrue(ReminderText.isValidReminderText("!@$#()_+")); // symbols only
        assertTrue(ReminderText.isValidReminderText("-1.122ewk:!@|!+@!*~")); // all kinds of symbols and alphanumerical
        assertTrue(ReminderText.isValidReminderText("! 1 wq ")); // with spaces
        assertTrue(ReminderText.isValidReminderText("            7")); // spaces with a value
    }
}
