package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

/**
 * Lists all goals in the CollegeZone to the user.
 */
public class ListGoalCommand extends Command {

    public static final String COMMAND_WORD = "listgoal";

    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_SUCCESS = "Listed all goals";


    @Override
    public CommandResult execute() {
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
