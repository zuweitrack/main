package seedu.address.testutil;

import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderText;

//@@author fuadsahmawi
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_END_DATE_TIME = "2017-04-08 13:30";
    public static final String DEFAULT_REMINDER_TEXT = "go home play game";
    public static final String DEFAULT_START_DATE_TIME = "2017-04-08 12:30";

    private EndDateTime endDateTime;
    private ReminderText reminderText;
    private DateTime dateTime;

    public ReminderBuilder() {
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
        reminderText = new ReminderText(DEFAULT_REMINDER_TEXT);
        dateTime = new DateTime(DEFAULT_START_DATE_TIME);
    }

    /**
     * Initializes the GoalBuilder with the data of {@code goalToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        endDateTime = reminderToCopy.getEndDateTime();
        reminderText = reminderToCopy.getReminderText();
        dateTime = reminderToCopy.getDateTime();
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code ReminderText} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminderText(String reminderText) {
        this.reminderText = new ReminderText(reminderText);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Goal} that we are building.
     */
    public ReminderBuilder withDateTime(String startDateTime) {
        this.dateTime = new DateTime(startDateTime);
        return this;
    }

    public Reminder build() {
        return new Reminder(reminderText, dateTime, endDateTime);
    }
}
