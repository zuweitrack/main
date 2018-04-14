package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;


//@@author sham-sheer
/**
 * Represents a Person's date of meeting in the address book.
 * Guarantees: immutable; is always valid
 */
public class Meet {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Make sure date is in this format: DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX =
            "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/" +
                    "(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\" +
                    "d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579]" +
                    "[26])00))))$";

    public final String value;

    public Meet(String meet) {
        requireNonNull(meet);
        if (meet.isEmpty()) {
            this.value = "";
        } else {
            checkArgument(isValidDate(meet), MESSAGE_DATE_CONSTRAINTS);
            this.value = meet;
        }
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meet // instanceof handles nulls
                && this.value.equals(((Meet) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();

    }
}
