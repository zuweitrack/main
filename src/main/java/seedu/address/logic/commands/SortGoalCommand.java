package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

/**
 * Sorts goal list based on sort field entered by user.
 */
public class SortGoalCommand extends Command {

    public static final String COMMAND_WORD = "sortgoal";
    public static final String COMMAND_ALIAS = "sgoal";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts CollegeZone's goals based on the field entered.\n"
            + "Parameters: FIELD (must be either 'importance', 'goaltext' or 'completion')\n"
            + "Example: " + COMMAND_WORD + " completion";

    public static final String MESSAGE_SUCCESS = "Sorted all goals by %s";
    private String sortField;

    public SortGoalCommand(String field) {
        this.sortField = field;
    }

    @Override
    public CommandResult execute() {
        model.sortGoal(sortField);
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortField));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortGoalCommand // instanceof handles nulls
                && sortField.equals(((SortGoalCommand) other).sortField));
    }
}
