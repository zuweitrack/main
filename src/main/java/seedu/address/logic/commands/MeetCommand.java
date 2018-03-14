
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;


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
            + PREFIX_DATE+ "01/April/2018";


    public static final String MESSAGE_SUCCESS = "Person added for meet up on this date. ";
    public static final String MESSAGE_DUPLICATE_MEETUP = "This meeting already exists in the calendar";
    public static final String MESSAGE_DATE_BOOKED = "This date has already been booked for a meeting with another person";

    public static final String MESSAGE_ARGUMENTS = "You are meeting person %1$d, Date of meeting: %2$s";

    private final Index targetIndex;
    private final String date;

    /**
     * @param targetIndex of the person in the filtered person list you want to meet
     * @param date you want to meet the person
     */

    public MeetCommand(Index targetIndex, String date) {
        requireNonNull(targetIndex);
        requireNonNull(date);

        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, targetIndex.getOneBased(), date));

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
