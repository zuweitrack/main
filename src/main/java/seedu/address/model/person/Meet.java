package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Calendar;
import java.util.GregorianCalendar;



//@@author sham-sheer
/**
 * Represents a Person's date of meeting in the address book.
 * Guarantees: immutable; is always valid
 */
public class Meet {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Make sure date is in this format: DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX =
            "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/"
                    + "(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\"
                    + "d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579]"
                    + "[26])00))))$";

    public final String value;

    public Meet(String meet) {
        requireNonNull(meet);
        if (meet.isEmpty()) {
            this.value = "";
        } else {
            checkArgument(isValidDate(meet), MESSAGE_DATE_CONSTRAINTS);
            this.value = meet;
        }
    }
    /**
     * Converts  date to seconds
     */
    public static long convertDateToSeconds(String date) {
        if (date == "") {
            return 0;
        }
        int day = Integer.parseInt(date.toString().substring(0,
                2));
        int month = Integer.parseInt(date.toString().substring(3,
                5));
        int year = Integer.parseInt(date.toString().substring(6,
                10));
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, day);
        long seconds = calendar.getTimeInMillis();
        return seconds;
    }

    /**
     * Converts meet date to a time that is relative to current date, for sorting purposes
     */
    public static long dateToInt(String date) {
        Calendar calendar = Calendar.getInstance();
        long longDate = convertDateToSeconds(date.toString());
        long currentDate = calendar.getTimeInMillis();
        long timeDiff = longDate - currentDate;
        if (timeDiff < 0) {
            return Long.MAX_VALUE;
        } else {
            return timeDiff;
        }
    }


    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meet // instanceof handles nulls
                && this.value.equals(((Meet) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();

    }
}
