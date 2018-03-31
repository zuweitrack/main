package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.GoalNotFoundException;

//@@author deborahlow97
/**
 * Deletes a goal identified using it's last displayed index from the address book.
 */
public class DeleteGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "-goal";

    public static final String COMMAND_ALIAS_1 = "-g";

    public static final String COMMAND_ALIAS_2 = "deletegoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the goal identified by the index number used in the goal listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GOAL_SUCCESS = "Deleted Goal: %1$s";

    private final Index targetIndex;

    private Goal goalToDelete;

    public DeleteGoalCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(goalToDelete);
        try {
            model.deleteGoal(goalToDelete);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_GOAL_SUCCESS, goalToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoalCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteGoalCommand) other).targetIndex) // state check
                && Objects.equals(this.goalToDelete, ((DeleteGoalCommand) other).goalToDelete));
    }
}
