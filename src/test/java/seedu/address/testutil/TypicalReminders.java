package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

//@@author fuadsahmawi
/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder REMINDER_A = new ReminderBuilder()
            .withDateTime("2017-04-08 12:30")
            .withEndDateTime("2017-04-08 14:30")
            .withReminderText("go to the gym").build();
    public static final Reminder REMINDER_B = new ReminderBuilder()
            .withDateTime("2017-06-08 15:30")
            .withEndDateTime("2017-06-08 17:30")
            .withReminderText("medical appointment").build();
    public static final Reminder REMINDER_C = new ReminderBuilder()
            .withDateTime("2017-05-10 12:30")
            .withEndDateTime("2017-05-10 14:30")
            .withReminderText("cc").build();
    public static final Reminder REMINDER_D = new ReminderBuilder()
            .withDateTime("2018-04-08 12:30")
            .withEndDateTime("2018-04-08 14:30")
            .withReminderText("dd").build();
    public static final Reminder REMINDER_E = new ReminderBuilder()
            .withDateTime("2017-07-07 13:30")
            .withEndDateTime("2017-07-07 14:30")
            .withReminderText("ee").build();
    public static final Reminder REMINDER_F = new ReminderBuilder()
            .withDateTime("2017-03-10 09:30")
            .withEndDateTime("2017-03-10 10:30")
            .withReminderText("ff").build();

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical reminders.
     */
    public static AddressBook getTypicalReminderAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reminder reminder : getTypicalReminders()) {
            try {
                ab.addReminder(reminder);
            } catch (DuplicateReminderException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REMINDER_A, REMINDER_B, REMINDER_C, REMINDER_D, REMINDER_E, REMINDER_F));
    }
}
