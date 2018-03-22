package seedu.address.model.person;

//import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's date of meeting in the address book.
 * Guarantees: immutable; is always valid
 */
public class Meet {
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Make sure date is in this format: DD/MM/YYYY";

    public final String value;

    public Meet(String meet) {
        //requireNonNull(meet);
        this.value = meet;
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
