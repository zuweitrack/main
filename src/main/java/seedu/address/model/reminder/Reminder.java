package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author fuadsahmawi

/**
 * Represents a Reminder
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {

    private final ReminderText reminderText;
    private final DateTime dateTime;

    /**
     * Every field must be present and not null.
     */

    public Reminder(ReminderText reminderText, DateTime dateTime) {
        requireAllNonNull(reminderText, dateTime);
        this.reminderText = reminderText;
        this.dateTime = dateTime;
    }

    public ReminderText getReminderText() {
        return reminderText;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getReminderText().equals(this.getReminderText())
                && otherReminder.getDateTime().equals(this.getDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(reminderText, dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Reminder: ")
                .append(getReminderText())
                .append(" Date & Time: ")
                .append(getDateTime());

        return builder.toString();
    }
}
