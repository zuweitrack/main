package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;

//@@author deborahlow97
/**
 * Adds a goal to the Goals Page.
 */
public class AddGoalCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "+goal";
    public static final String COMMAND_ALIAS_1 = "+g";
    public static final String COMMAND_ALIAS_2 = "addgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a goal to Goals Page. \n"
            + "Parameters: "
            + PREFIX_IMPORTANCE + "IMPORTANCE "
            + PREFIX_GOAL_TEXT + "TEXT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IMPORTANCE + "3 "
            + PREFIX_GOAL_TEXT + "lose weight \n";

    public static final String MESSAGE_SUCCESS = "New goal added: %1$s";
    public static final String MESSAGE_DUPLICATE_GOAL = "This goal already exists in the Goals Page";

    private final Goal toAdd;

    /**
     * Creates an AddGoalCommand to add the specified {@code Goal}
     */
    public AddGoalCommand(Goal goal) {
        requireNonNull(goal);
        toAdd = goal;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addGoal(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateGoalException e) {
            throw new CommandException(MESSAGE_DUPLICATE_GOAL);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGoalCommand // instanceof handles nulls
                && toAdd.equals(((AddGoalCommand) other).toAdd));
    }
}
