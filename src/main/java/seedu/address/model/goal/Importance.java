package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author deborahlow97
/**
 * Represents a Goal's importance in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidImportance(String)}
 */
public class Importance {


    public static final String MESSAGE_IMPORTANCE_CONSTRAINTS =
            "Importance should only be a numerical integer value between 1 to 10.";
    public static final String IMPORTANCE_VALIDATION_REGEX = "[0-9]+";
    private static final int MINIMUM_IMPORTANCE = 1;
    private static final int MAXIMUM_IMPORTANCE = 10;
    private static int importanceInIntegerForm;
    public final String value;

    /**
     * Constructs a {@code Importance}.
     *
     * @param importance A valid importance.
     */
    public Importance(String importance) {
        requireNonNull(importance);
        checkArgument(isValidImportance(importance), MESSAGE_IMPORTANCE_CONSTRAINTS);
        this.value = importance;
    }

    /**
     * Returns true if a given string is a valid goal importance.
     */
    public static boolean isValidImportance(String test) {
        return test.matches(IMPORTANCE_VALIDATION_REGEX) && isAnIntegerWithinRange(test);
    }

    /**
     * Returns true if a given string is an integer and within range of importance.
     */
    private static boolean isAnIntegerWithinRange(String test) {
        importanceInIntegerForm = Integer.parseInt(test);
        if (importanceInIntegerForm >= MINIMUM_IMPORTANCE
                && importanceInIntegerForm <= MAXIMUM_IMPORTANCE) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Importance // instanceof handles nulls
                && this.value.equals(((Importance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
