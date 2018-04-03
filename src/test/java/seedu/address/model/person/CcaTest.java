package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author deborahlow97
public class CcaTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Cca(null));
    }

    @Test
    public void constructor_invalidCcaName_throwsIllegalArgumentException() {
        String invalidCcaName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Cca(invalidCcaName));
    }

    @Test
    public void isValidCcaName() {
        // null cca name
        Assert.assertThrows(NullPointerException.class, () -> Cca.isValidCcaName(null));

        // invalid cca name
        assertFalse(Cca.isValidCcaName("!3")); // contains '!'
        assertFalse(Cca.isValidCcaName("abc%")); // contains '%'
        assertFalse(Cca.isValidCcaName("abc-1")); // contains '-'
        assertFalse(Cca.isValidCcaName("abc@@@1")); // contains '@'

        // valid cca name
        assertTrue(Cca.isValidCcaName("Hackathon")); // alphabets
        assertTrue(Cca.isValidCcaName("Walkathon 2018")); // using .alphanumeric with spaces
        assertTrue(Cca.isValidCcaName("Basketball")); // valid alphabets
        assertTrue(Cca.isValidCcaName("Hackathon2018")); //alphanumeric
    }
}
