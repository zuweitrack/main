package seedu.address.model.goal;

import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;
import static seedu.address.logic.parser.DateTimeParser.properDateTimeFormat;

import java.time.LocalDateTime;

//@@author deborahlow97
/**
 * Represents a Goal's end date and time in the Goals Page.
 * Guarantees: immutable; is valid
 */
public class EndDateTime {

    public final String value;

    /**
     * Constructs a {@code EndDateTime}.
     *
     * @param endDateTime A valid endDateTime number.
     */
    public EndDateTime(String endDateTime) {
        if (endDateTime.equals("")) {
            this.value = "";
        } else {
            LocalDateTime localEndDateTime = nattyDateAndTimeParser(endDateTime).get();
            this.value = properDateTimeFormat(localEndDateTime);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.value.equals(((EndDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
