package seedu.address.model.util;

import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderText;

//@@author fuadsahmawi
/**
 * Contains utility methods for populating {@code CollegeZone} with sample reminder data.
 */
public class SampleReminderDataUtil {

    public static Reminder[] getSampleReminders() {
        return new Reminder[] {

            new Reminder(new ReminderText("CS2103T Submission"),
                    new DateTime("2018-04-15 23:00"),
                    new EndDateTime("2018-04-15 23:59")),
            new Reminder(new ReminderText("Gym Session"),
                    new DateTime("2018-04-13 14:00"),
                    new EndDateTime("2018-04-13 16:00")),
            new Reminder(new ReminderText("Gym Session"),
                    new DateTime("2018-04-06 14:00"),
                    new EndDateTime("2018-04-06 16:00")),
            new Reminder(new ReminderText("Gym Session"),
                    new DateTime("2018-04-20 14:00"),
                    new EndDateTime("2018-04-20 16:00")),
            new Reminder(new ReminderText("Gym Session"),
                    new DateTime("2018-04-27 14:00"),
                    new EndDateTime("2018-04-27 16:00")),
            new Reminder(new ReminderText("Recess Week"),
                    new DateTime("2018-04-23 00:00"),
                    new EndDateTime("2018-04-27 23:59")),
            new Reminder(new ReminderText("CS2103T Software Demo"),
                    new DateTime("2018-04-20 09:00"),
                    new EndDateTime("2018-04-20 10:00")),
            new Reminder(new ReminderText("CS2103T Group Meeting"),
                    new DateTime("2018-04-14 11:00"),
                    new EndDateTime("2018-04-14 18:00")),
            new Reminder(new ReminderText("Chalet"),
                    new DateTime("2018-04-21 10:00"),
                    new EndDateTime("2018-04-22 20:00")),
            new Reminder(new ReminderText("Medical Appointment"),
                    new DateTime("2018-04-05 15:00"),
                    new EndDateTime("2018-04-05 17:00")),
        };
    }
}
