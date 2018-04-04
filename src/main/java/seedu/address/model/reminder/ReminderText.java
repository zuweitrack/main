package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author fuadsahmawi
/**
 * Represents a Reminder's text in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminderText(String)}
 */
public class ReminderText {

    public static final String MESSAGE_REMINDER_TEXT_CONSTRAINTS =
            "Reminder text can be any expression that are not just whitespaces.";
    public static final String REMINDER_TEXT_VALIDATION_REGEX = "^(?!\\s*$).+";
    public final String reminderText;

    /**
     * Constructs a {@code ReminderText}.
     *
     * @param reminderText A valid reminder text.
     */
    public ReminderText(String reminderText) {
        requireNonNull(reminderText);
        checkArgument(isValidGoalText(reminderText), MESSAGE_REMINDER_TEXT_CONSTRAINTS);
        this.reminderText = reminderText;
    }

    /**
     * Returns true if a given string is a valid reminder text.
     */
    public static boolean isValidReminderText(String test) {
        return test.matches(REMINDER_TEXT_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid reminder text.
     */
    public static boolean isValidGoalText(String test) {
        return test.matches(REMINDER_TEXT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return reminderText;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.goal.GoalText // instanceof handles nulls
                && this.reminderText.equals(((
                        seedu.address.model.reminder.ReminderText) other).reminderText)); // state check
    }

    @Override
    public int hashCode() {
        return reminderText.hashCode();
    }

}
