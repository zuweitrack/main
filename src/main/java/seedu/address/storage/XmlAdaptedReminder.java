package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderText;

//@@author fuadsahmawi
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    @XmlElement(required = true)
    private String reminderText;
    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String endDateTime;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Constructs an {@code XmlAdaptedReminder} with the given person details.
     */
    public XmlAdaptedReminder(String reminderText, String dateTime, String endDateTime) {
        this.reminderText = reminderText;
        this.dateTime = dateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(Reminder source) {
        reminderText = source.getReminderText().toString();
        dateTime = source.getDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        if (this.reminderText == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReminderText.class.getSimpleName()));
        }
        if (!ReminderText.isValidReminderText(this.reminderText)) {
            throw new IllegalValueException(ReminderText.MESSAGE_REMINDER_TEXT_CONSTRAINTS);
        }
        final ReminderText reminderText = new ReminderText(this.reminderText);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        final DateTime dateTime = new DateTime(this.dateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(this.endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        final EndDateTime endDateTime = new EndDateTime(this.endDateTime);

        return new Reminder(reminderText, dateTime, endDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReminder)) {
            return false;
        }

        XmlAdaptedReminder otherPerson = (XmlAdaptedReminder) other;
        return Objects.equals(reminderText, otherPerson.reminderText)
                && Objects.equals(dateTime, otherPerson.dateTime)
                && Objects.equals(endDateTime, otherPerson.endDateTime);
    }
}
