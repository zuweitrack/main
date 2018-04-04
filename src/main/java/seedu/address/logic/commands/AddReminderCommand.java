package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TEXT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

//@@author fuadsahmawi
/**
 * Adds a reminder to the Calendar.
 */
public class AddReminderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "+reminder";
    public static final String COMMAND_ALIAS = "+r";
    public static final String COMMAND_ALIAS_2 = "addreminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to Calendar. "
            + "Parameters: "
            + PREFIX_REMINDER_TEXT + "TEXT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_TEXT + " do homework ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the Calendar";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder}
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
