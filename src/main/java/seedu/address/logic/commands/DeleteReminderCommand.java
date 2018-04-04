package seedu.address.logic.commands;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderTextPredicate;

//@@author fuadsahmawi
/**
 * Deletes a reminder identified using its title in the calendar
 */
public class DeleteReminderCommand {
    public static final String COMMAND_WORD = "+reminder";
    public static final String COMMAND_ALIAS = "+r";
    public static final String COMMAND_ALIAS_2 = "addreminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by its title in the calendar.\n"
            + "Parameters: REMINDER_TITLE\n"
            + "Example: " + COMMAND_WORD + " Eat pills";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";
    
    private ReminderTextPredicate predicate;
    
    private Reminder reminderToDelete;

    public DeleteReminderCommand(ReminderTextPredicate predicate) {
        this.predicate = predicate;
    }
}
