package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author sham-sheer
public class MeetTest {

    @Test
    public void equals() {
        Meet meet = new Meet("14/01/2018");

        // same object -> return true
        assertTrue(meet.equals(meet));

        // same values -> returns true
        Meet meetDuplicate = new Meet(meet.value);
        assertTrue(meet.equals(meetDuplicate));

        // different types -> returns false
        assertFalse(meet.equals("14/01/2018"));

        // null -> returns false
        assertFalse(meet.equals(null));

        // different meet -> returns false;
        Meet differentMeet = new Meet("15/01/2018");
        assertFalse(meet.equals(differentMeet));
    }

    @Test
    public void isValidDate() {
        // null meet date
        Assert.assertThrows(NullPointerException.class, () -> Meet.isValidDate(null));

        // blank meet date
        assertFalse(Meet.isValidDate("")); // empty string
        assertFalse(Meet.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Meet.isValidDate("12--1997")); // missing month part
        assertFalse(Meet.isValidDate("--12-1998")); // missing date part
        assertFalse(Meet.isValidDate("//12/1998")); // missing date part
        assertFalse(Meet.isValidDate("12-12-")); // missing year part
        assertFalse(Meet.isValidDate("12/12/")); // missing year part


        // invalid parts
        assertFalse(Meet.isValidDate("32-Jan-2000")); // invalid day
        assertFalse(Meet.isValidDate("33.01.2000")); // invalid day
        assertFalse(Meet.isValidDate("20/20/2000")); // invalid month
        assertFalse(Meet.isValidDate("20/13/1997")); // invalid month
        assertFalse(Meet.isValidDate("29/Feb/2001")); // invalid due to leap year
        assertFalse(Meet.isValidDate("31/04/2000")); // invalid day for month of April
        assertFalse(Meet.isValidDate("31/Sep/2000")); // invalid day for month of September
        assertFalse(Meet.isValidDate("31//01/2000")); // invalid meet date format
        assertFalse(Meet.isValidDate("31..01..2000")); // invalid meet date format
        assertFalse(Meet.isValidDate("20--2-1997")); // invalid meet date format
        assertFalse(Meet.isValidDate("20*2*1997")); // invalid symbols
        assertFalse(Meet.isValidDate("12 / 12 / 2012")); // contains spaces
        assertFalse(Meet.isValidDate("01/Jan/2000"));  // using /
        assertFalse(Meet.isValidDate("31.Jan.2000"));   // using .
        assertFalse(Meet.isValidDate("01-12-2000")); // using -
        assertFalse(Meet.isValidDate("28-Feb-2001"));

        // valid meet date
        assertTrue(Meet.isValidDate("01/01/2000"));
    }
}
