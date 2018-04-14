package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author sham-sheer
/**
 * Sort the persons in CollegeZone based on the users parameters
 */
public class SortCommand extends UndoableCommand {

    public static final  String COMMAND_WORD = "sort";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid sort type: %1$s";

    public static final String MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP = "List sorted according to LOF!";

    public static final String MESSAGE_SORTED_SUCCESS_MEET_DATE = "List sorted according to your latest meet date!";

    public static final String MESSAGE_SORTED_SUCCESS_BIRTHDAY = "List sorted according to your latest birthday!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the person list identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    public SortCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortPersons(index);
        } catch (IndexOutOfBoundsException ioe) {
            throw new AssertionError("The index is out of bounds");
        }
        if (index.getOneBased() == 1) {
            return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP));
        }
        if (index.getOneBased() == 2) {
            return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_MEET_DATE));
        }
        return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_BIRTHDAY));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (index.getOneBased() > 3) {
            throw new CommandException(String.format(SortCommand.MESSAGE_INVALID_COMMAND_FORMAT, index.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.index.equals(((SortCommand) other).index)); // state check
    }
}

