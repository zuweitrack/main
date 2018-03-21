package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


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
}
