package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.DateTimeParser.properDateTimeFormat;

import java.time.LocalDateTime;

//@@author deborahlow97
/**
 * Represents a Goal's start date in the address book.
 */
public class StartDateTime {

    public final String value;

    /**
     * Constructs a {@code StartDateTime}.
     *
     * @param startDateTime A valid LocalDateTime.
     */
    public StartDateTime(LocalDateTime startDateTime) {
        requireNonNull(startDateTime);
        this.value = properDateTimeFormat(startDateTime);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                && this.value.equals(((StartDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
