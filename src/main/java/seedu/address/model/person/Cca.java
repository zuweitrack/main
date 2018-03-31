package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author deborahlow97
/**
 * Represents a Person's CCAs in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaName(String)}
 */
public class Cca {

    public static final String MESSAGE_CCA_CONSTRAINTS = "CCAs should be in alphanumeric";
    public static final String CCA_VALIDATION_REGEX = "\\s*\\p{Alnum}[\\p{Alnum}\\s]*";
    public final String ccaName;

    /**
     * Constructs a {@code CCA}.
     *
     * @param ccaName A valid CCA.
     */
    public Cca(String ccaName) {
        requireNonNull(ccaName);
        checkArgument(isValidCcaName(ccaName), MESSAGE_CCA_CONSTRAINTS);
        this.ccaName = ccaName;
    }

    /**
     * Returns true if a given string is a valid CCA name.
     */
    public static boolean isValidCcaName(String test) {
        return test.matches(CCA_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cca // instanceof handles nulls
                && this.ccaName.equals(((Cca) other).ccaName)); // state check
    }

    @Override
    public int hashCode() {
        return ccaName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + ccaName + ']';
    }

}
