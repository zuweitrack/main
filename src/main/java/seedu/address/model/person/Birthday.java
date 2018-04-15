package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.DateTimeParser.containsDateAndTime;
import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

import java.time.LocalDateTime;

//@@author deborahlow97
/**
 * Represents a Person's birthday in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person birthday should be a valid date.";
    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)"
                    + "\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)"
                    + "\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|"
                    + "[3579][26])00))))$";

    public final String value;

    /**
     * Constructs an {@code Birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthdayRegex(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        this.value = birthday;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthdayRegex(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a valid person birthday (before current date).
     */
    public static boolean isValidBirthday(String test) {
        LocalDateTime birthdayLocalDateTime;
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        String birthdayInDifferentFormat = getDifferentBirthdayFormat(test);
        if (containsDateAndTime(birthdayInDifferentFormat)) {
            birthdayLocalDateTime = nattyDateAndTimeParser(birthdayInDifferentFormat).get();
        } else {
            return false;
        }
        return isBeforeCurrentDate(birthdayLocalDateTime, currentLocalDateTime);
    }

    /**
     * Takes in @param birthdayLocalDateTime and @param currentLocalDateTime and checks if 1st parameter is later
     * than the second parameter
     * @return boolean
     */
    private static boolean isBeforeCurrentDate(LocalDateTime birthdayLocalDateTime,
                                               LocalDateTime currentLocalDateTime) {
        if (birthdayLocalDateTime.isBefore(currentLocalDateTime)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     *
     * Takes in @param date in dd/mm/yyyy format
     * @return birthday string in mm/dd/yyyy format
     */
    public static String getDifferentBirthdayFormat(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6, 10);
        StringBuilder builder = new StringBuilder();
        builder.append(month)
                .append("/")
                .append(day)
                .append("/")
                .append(year);
        return builder.toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
