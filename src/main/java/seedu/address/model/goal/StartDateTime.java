package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.DateTimeParser.getLocalDateTimeFromProperDateTime;
import static seedu.address.logic.parser.DateTimeParser.properDateTimeFormat;

import java.time.LocalDateTime;

//@@author deborahlow97
/**
 * Represents a Goal's start date in the address book.
 */
public class StartDateTime implements Comparable<StartDateTime> {

    public final String value;
    public final LocalDateTime localDateTimeValue;


    /**
     * Constructs a {@code StartDateTime}.
     *
     * @param startDateTime A valid LocalDateTime.
     */
    public StartDateTime(LocalDateTime startDateTime) {
        requireNonNull(startDateTime);
        this.localDateTimeValue = startDateTime;
        this.value = properDateTimeFormat(startDateTime);
    }

    public StartDateTime(String startDateTimeInString) {
        requireNonNull(startDateTimeInString);
        this.value = startDateTimeInString;
        this.localDateTimeValue = getLocalDateTimeFromProperDateTime(startDateTimeInString);
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

    @Override
    public int compareTo(StartDateTime startDateTime) {
        if ((startDateTime.localDateTimeValue).isEqual(this.localDateTimeValue)) {
            return 0;
        } else if ((startDateTime.localDateTimeValue).isBefore(this.localDateTimeValue)) {
            return 1;
        } else if ((startDateTime.localDateTimeValue).isAfter(this.localDateTimeValue)) {
            return -1;
        }
        return 0;
    }
}
