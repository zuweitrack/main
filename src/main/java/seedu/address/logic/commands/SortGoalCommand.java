package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.exceptions.EmptyGoalListException;

//@@author deborahlow97
/**
 * Sorts goal list in CollegeZone based on sort field entered by user.
 */
public class SortGoalCommand extends Command {

    public static final String COMMAND_WORD = "sortgoal";
    public static final String COMMAND_ALIAS = "sgoal";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts CollegeZone's goals based on the field entered.\n"
            + "Parameters: "
            + PREFIX_SORT_FIELD + "FIELD (must be 'importance', 'startdatetime' or 'completion') "
            + PREFIX_SORT_ORDER + "ORDER (must be either 'ascending' or 'descending')\n"
            + "Example: " + COMMAND_WORD + " f/completion o/ascending";

    public static final String MESSAGE_SUCCESS = "Sorted all goals by %s and %s";
    private String sortField;
    private String sortOrder;

    public SortGoalCommand(String field, String order) {
        this.sortField = field;
        this.sortOrder = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.sortGoal(sortField, sortOrder);
        } catch (EmptyGoalListException egle) {
            throw new CommandException(Messages.MESSAGE_INVALID_SORT_COMMAND_USAGE);
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortField, sortOrder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortGoalCommand // instanceof handles nulls
                && sortField.equals(((SortGoalCommand) other).sortField));
    }
}
