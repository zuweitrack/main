package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;

//@@author deborahlow97
/**
 * Represents a Goal's completion status in the Goals Page.
 */
public class Completion {
    public final String value;
    public final boolean hasCompleted;

    /**
     * Constructs a {@code Completion}.
     *
     * @param isCompleted A valid boolean.
     */
    public Completion(Boolean isCompleted) {
        requireNonNull(isCompleted);
        if (isCompleted) {
            this.hasCompleted = true;
            this.value = "true";
        } else {
            this.hasCompleted = false;
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

//    @Override
//    public int compareTo(Completion completion) {
//        return completion.hasCompleted.compareTo(this.hasCompleted);
//    }
}
