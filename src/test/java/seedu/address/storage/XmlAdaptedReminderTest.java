package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedReminder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalReminders.REMINDER_B;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.ReminderText;
import seedu.address.testutil.Assert;

//@@author fuadsahmawi
public class XmlAdaptedReminderTest {

    private static final String INVALID_REMINDER_TEXT = " ";

    private static final String VALID_REMINDER_TEXT = REMINDER_B.getReminderText().toString();
    private static final String VALID_START_DATE_TIME = REMINDER_B.getDateTime().toString();
    private static final String VALID_END_DATE_TIME = REMINDER_B.getEndDateTime().toString();

    @Test
    public void toModelType_invalidReminderText_throwsIllegalValueException() {
        XmlAdaptedReminder reminder =
                new XmlAdaptedReminder(INVALID_REMINDER_TEXT, VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = ReminderText.MESSAGE_REMINDER_TEXT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullReminderText_throwsIllegalValueException() {
        XmlAdaptedReminder reminder = new XmlAdaptedReminder(null, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ReminderText.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }


    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedReminder reminder = new XmlAdaptedReminder(VALID_REMINDER_TEXT, null, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedReminder reminder = new XmlAdaptedReminder(VALID_REMINDER_TEXT, VALID_START_DATE_TIME,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reminder::toModelType);
    }
}
