package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class UnitNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UnitNumber(null));
    }

    @Test
    public void constructor_invalidUnitNumber_throwsIllegalArgumentException() {
        String invalidUnitNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new UnitNumber(invalidUnitNumber));
    }

    @Test
    public void isValidUnitNumber() {
        // null unit number
        Assert.assertThrows(NullPointerException.class, () -> UnitNumber.isValidUnitNumber(null));

        // invalid unit numbers
        assertFalse(UnitNumber.isValidUnitNumber("")); // empty string
        assertFalse(UnitNumber.isValidUnitNumber(" ")); // spaces only
        assertFalse(UnitNumber.isValidUnitNumber("#12222-1312414")); // long unit number
        assertFalse(UnitNumber.isValidUnitNumber("#1-1")); // one character only

        // valid unit numbers
        assertTrue(UnitNumber.isValidUnitNumber("#01-355"));
        assertTrue(UnitNumber.isValidUnitNumber("#1-12"));
        assertTrue(UnitNumber.isValidUnitNumber("#12-12"));

    }
}
