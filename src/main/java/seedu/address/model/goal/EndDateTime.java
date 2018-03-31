package seedu.address.model.goal;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;
import static seedu.address.logic.parser.DateTimeParser.properDateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

//@@author deborahlow97
/**
 * Represents a Goal's end date and time in the Goals Page.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDateTime(String)}
 */
public class EndDateTime {


    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS =
            "EndDateTime must be a valid date and time";
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
            checkArgument(isValidEndDateTime(endDateTime), MESSAGE_END_DATE_TIME_CONSTRAINTS);
            LocalDateTime localEndDateTime = nattyDateAndTimeParser(endDateTime).get();
            this.value = properDateTimeFormat(localEndDateTime);
        }
    }

    /**
     * Returns true if a given string is a valid person endDateTime number.
     */
    public static boolean isValidEndDateTime(String test) {
        Optional<LocalDateTime> localEndDateTime = nattyDateAndTimeParser(test);
        if (localEndDateTime.isPresent()) {
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
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.value.equals(((EndDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
