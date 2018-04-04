
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Meet;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Adds a meeting to the address book.
 */
public class MeetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "meet";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the date of meetup for the person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01/April/2018";


    public static final String MESSAGE_ADD_MEETDATE_SUCCESS = "%1$s added for meet up! Here particulars "
            + "have been added to the calendar.";
    public static final String MESSAGE_DELETE_MEETDATE_SUCCESS = "You are not meeting %1$s anymore!!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person has already been set to have meeting.";

    private final Index targetIndex;
    private final Meet date;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param targetIndex of the person in the filtered person list you want to meet
     * @param date you want to meet the person
     */

    public MeetCommand(Index targetIndex, Meet date) {
        requireNonNull(targetIndex);
        requireNonNull(date);

        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getBirthday(),
                personToEdit.getLevelOfFriendship(), personToEdit.getUnitNumber(), personToEdit.getCcas(),
                date, personToEdit.getTags());
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !date.value.isEmpty() ? MESSAGE_ADD_MEETDATE_SUCCESS : MESSAGE_DELETE_MEETDATE_SUCCESS;
        return String.format(message, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetCommand)) {
            return false;
        }

        // state check
        MeetCommand e = (MeetCommand) other;
        return targetIndex.equals(e.targetIndex)
                && date.equals(e.date);
    }





}
