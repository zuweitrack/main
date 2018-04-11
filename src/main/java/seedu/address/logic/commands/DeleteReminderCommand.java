package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderTextPredicate;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

//@@author fuadsahmawi
/**
 * Deletes a reminder identified using its title in the calendar
 */
public class DeleteReminderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "-reminder";
    public static final String COMMAND_ALIAS = "-r";
    public static final String COMMAND_ALIAS_2 = "deletereminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by its title & start time in the calendar.\n"
            + "Parameters: REMINDER_TITLE & START_DATETIME\n"
            + "Example: " + COMMAND_WORD + "text/Eat pills d/tmr 8pm";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private Index targetIndex;

    private String dateTime;

    private ReminderTextPredicate predicate;

    private Reminder reminderToDelete;

    public DeleteReminderCommand(ReminderTextPredicate predicate, String dateTime) {
        this.predicate = predicate;
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(reminderToDelete);
        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        model.updateFilteredReminderList(predicate);
        List<Reminder> lastShownList = model.getFilteredReminderList();
        targetIndex = Index.fromOneBased(1);
        if (lastShownList.size() > 1) {
            for (Reminder reminder : lastShownList) {
                if (reminder.getDateTime().toString().equals(dateTime)) {
                    reminderToDelete = reminder;
                }
            }
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_TEXT_DATE);
            }

            reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        }
    }
}
