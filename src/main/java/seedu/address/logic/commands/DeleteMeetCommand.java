package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes the meet up set with a person using the person's displayed index from the address book.
 */
public class DeleteMeetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "-meet";

    public static final String COMMAND_ALIAS = "-m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's meet date identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "You are not meeting %1$s anymore. ";

    private final Index targetIndex;

    private Person personToDelete;

    public DeleteMeetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(personToDelete);
        try {
            model.deleteMeetDate(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteMeetCommand) other).targetIndex) // state check
                && Objects.equals(this.personToDelete, ((DeleteMeetCommand) other).personToDelete));
    }


}
