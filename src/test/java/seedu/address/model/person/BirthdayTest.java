package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // blank birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("12--1997")); // missing month part
        assertFalse(Birthday.isValidBirthday("--12-1998")); // missing date part
        assertFalse(Birthday.isValidBirthday("//12/1998")); // missing date part
        assertFalse(Birthday.isValidBirthday("12-12-")); // missing year part
        assertFalse(Birthday.isValidBirthday("12/12/")); // missing year part


        // invalid parts
        assertFalse(Birthday.isValidBirthday("32-Jan-2000")); // invalid day
        assertFalse(Birthday.isValidBirthday("33.01.2000")); // invalid day
        assertFalse(Birthday.isValidBirthday("20/20/2000")); // invalid month
        assertFalse(Birthday.isValidBirthday("20/13/1997")); // invalid month
        assertFalse(Birthday.isValidBirthday("29/Feb/2001")); // invalid due to leap year
        assertFalse(Birthday.isValidBirthday("31/04/2000")); // invalid day for month of April
        assertFalse(Birthday.isValidBirthday("31/Sep/2000")); // invalid day for month of September
        assertFalse(Birthday.isValidBirthday("31//01/2000")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("31..01..2000")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("20--2-1997")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("20*2*1997")); // invalid symbols
        assertFalse(Birthday.isValidBirthday("12 / 12 / 2012")); // contains spaces

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01/01/2000"));
        assertTrue(Birthday.isValidBirthday("01/Jan/2000"));  // using /
        assertTrue(Birthday.isValidBirthday("31.Jan.2000"));   // using .
        assertTrue(Birthday.isValidBirthday("01-12-2000")); // using -
        assertTrue(Birthday.isValidBirthday("28-Feb-2001"));  // valid date
    }
}
