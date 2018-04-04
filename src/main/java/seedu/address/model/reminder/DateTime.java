package seedu.address.model.reminder;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

import java.time.LocalDateTime;
import java.util.Optional;

//@author fuadsahmawi

/**
 * Represents a Reminder's date and time in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS =
            "DateTime must be a valid date and time";
    public final String dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid DateTime number.
     */
    public DateTime(String dateTime) {
        if (dateTime.equals("")) {
            this.dateTime = "";
        } else {
            checkArgument(isValidDateTime(dateTime), MESSAGE_DATE_TIME_CONSTRAINTS);
            this.dateTime = dateTime;
        }
    }

    /**
     * Returns true if a given string is a valid person endDateTime number.
     */
    public static boolean isValidDateTime(String test) {
        Optional<LocalDateTime> localEndDateTime = nattyDateAndTimeParser(test);
        if (localEndDateTime.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
