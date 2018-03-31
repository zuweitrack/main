package seedu.address.model.goal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class ImportanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Importance(null));
    }

    @Test
    public void constructor_invalidImportance_throwsIllegalArgumentException() {
        String invalidImportance = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Importance(invalidImportance));
    }

    @Test
    public void isValidImportance() {
        // null importance
        Assert.assertThrows(NullPointerException.class, () -> Importance.isValidImportance(null));

        // blank importance
        assertFalse(Importance.isValidImportance("")); // empty string
        assertFalse(Importance.isValidImportance(" ")); // spaces only

        // invalid parts
        assertFalse(Importance.isValidImportance("22")); // invalid positive level of friendship
        assertFalse(Importance.isValidImportance("-1")); // invalid negative level of friendship
        assertFalse(Importance.isValidImportance("11")); // invalid positive level of friendship
        assertFalse(Importance.isValidImportance("a")); // invalid character
        assertFalse(Importance.isValidImportance("11a")); // invalid extra character and number
        assertFalse(Importance.isValidImportance("10b")); // invalid extra character
        assertFalse(Importance.isValidImportance("9*")); // '*' symbol
        assertFalse(Importance.isValidImportance("^")); // '^' symbol
        assertFalse(Importance.isValidImportance("0")); // invalid number
        assertFalse(Importance.isValidImportance("1.1")); // number in decimal

        // valid importance
        assertTrue(Importance.isValidImportance("1"));
        assertTrue(Importance.isValidImportance("10"));  // minimal
        assertTrue(Importance.isValidImportance("2"));   // alphabets only
        assertTrue(Importance.isValidImportance("5")); // special characters local part
        assertTrue(Importance.isValidImportance("7"));  // numeric local part and domain name
    }
}
