package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class LevelOfFriendshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LevelOfFriendship(null));
    }

    @Test
    public void constructor_invalidLevelOfFriendship_throwsIllegalArgumentException() {
        String invalidLevelOfFriendship = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new LevelOfFriendship(invalidLevelOfFriendship));
    }

    @Test
    public void isValidLevelOfFriendship() {
        // null level of friendship
        Assert.assertThrows(NullPointerException.class, () -> LevelOfFriendship.isValidLevelOfFriendship(null));

        // blank level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("")); // empty string
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship(" ")); // spaces only

        // invalid parts
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("22")); // invalid positive level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("-1")); // invalid negative level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("11")); // invalid positive level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("a")); // invalid character
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("11a")); // invalid extra character and number
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("10b")); // invalid extra character
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("9*")); // '*' symbol
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("^")); // '^' symbol
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("0")); // invalid number
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("1.1")); // number in decimal

        // valid level of friendship
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("1"));
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("10"));  // minimal
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("2"));   // alphabets only
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("5")); // special characters local part
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("7"));  // numeric local part and domain name
    }
}
