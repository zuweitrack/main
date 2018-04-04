package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;

//@@author deborahlow97
/**
 * Represents a Goal's completion status in the Goals Page.
 */
public class Completion {
    public final String value;

    /**
     * Constructs a {@code Completion}.
     *
     * @param completion A valid boolean.
     */
    public Completion(Boolean completion) {
        requireNonNull(completion);
        if (completion) {
            this.value = "true";
        } else {
            this.value = "false";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Completion // instanceof handles nulls
                && this.value.equals(((Completion) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
