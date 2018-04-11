package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TEXT;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminder.Reminder;

//@@author fuadsahmawi
/**
 * A utility class for Reminder.
 */
public class ReminderUtil {
    
    /**
     * Returns an addreminder command string for adding the {@code reminder}.
     */
    public static String getAddReminderCommand(Reminder reminder) {
        return AddReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code goal}'s details.
     */
    public static String getReminderDetails(Reminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER_TEXT + reminder.getReminderText().toString() + " ");
        sb.append(PREFIX_DATE + reminder.getDateTime().toString() + " ");
        sb.append(PREFIX_END_DATE + reminder.getEndDateTime().toString() + " ");
        return sb.toString();
    }
}
