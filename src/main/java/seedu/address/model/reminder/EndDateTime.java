package seedu.address.model.reminder;

//@@author fuadsahmawi

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a Reminder's end date and time in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDateTime(String)}
 */
public class EndDateTime {


    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS =
            "EndDateTime must be a valid date and time";
    public final String endDateTime;

    /**
     * Constructs a {@code EndDateTime}.
     *
     * @param endDateTime A valid endDateTime number.
     */
    public EndDateTime(String endDateTime) {
        if (endDateTime.equals("")) {
            this.endDateTime = "";
        } else {
            checkArgument(isValidEndDateTime(endDateTime), MESSAGE_END_DATE_TIME_CONSTRAINTS);
            this.endDateTime = endDateTime;
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
        return endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.endDateTime.equals(((EndDateTime) other).endDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return endDateTime.hashCode();
    }
}
