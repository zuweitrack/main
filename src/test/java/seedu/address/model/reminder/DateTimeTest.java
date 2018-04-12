package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author fuadsahmawi
public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void isValidGoalText() {
        // null reminder text
        Assert.assertThrows(NullPointerException.class, () -> ReminderText.isValidReminderText(null));

        // blank reminder text
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("aaa0")); // alphanumerical
        assertFalse(DateTime.isValidDateTime("!@$#()_+")); // symbols only

        // valid reminder text
        assertTrue(DateTime.isValidDateTime("1"));
        assertTrue(DateTime.isValidDateTime("-1.122ewk:!@|!+@!*~")); // all kinds of symbols and alphanumerical
        assertTrue(DateTime.isValidDateTime("! 1 wq ")); // with spaces
        assertTrue(DateTime.isValidDateTime("            7")); // spaces with a value
    }
}
