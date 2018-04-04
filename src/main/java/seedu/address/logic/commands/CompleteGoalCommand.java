package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;

import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;
import seedu.address.model.goal.exceptions.GoalNotFoundException;

//@@author deborahlow97
/**
 * Edits the details of an existing goal in the address book.
 */
public class CompleteGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "!goal";
    public static final String COMMAND_ALIAS_1 = "!g";
    public static final String COMMAND_ALIAS_2 = "completedgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate completion of the goal identified "
            + "by the index number used in the last goal listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_COMPLETE_GOAL_SUCCESS = "Completed Goal! : %1$s";

    private final Index index;
    private final CompleteGoalDescriptor completeGoalDescriptor;

    private Goal goalToUpdate;
    private Goal updatedGoal;

    /**
     * @param index of the goal in the filtered goal list to update
     */
    public CompleteGoalCommand(Index index, CompleteGoalDescriptor completeGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(completeGoalDescriptor);

        this.index = index;
        this.completeGoalDescriptor = new CompleteGoalDescriptor(completeGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoalWithoutParameters(goalToUpdate, updatedGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_COMPLETE_GOAL_SUCCESS, updatedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToUpdate = lastShownList.get(index.getZeroBased());
        updatedGoal = createUpdatedGoal(goalToUpdate, completeGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToUpdate}
     * edited with {@code completeGoalDescriptor}.
     */
    private static Goal createUpdatedGoal(Goal goalToUpdate, CompleteGoalDescriptor completeGoalDescriptor) {
        assert goalToUpdate != null;

        GoalText goalText = completeGoalDescriptor.getGoalText().orElse(goalToUpdate.getGoalText());
        Importance importance = completeGoalDescriptor.getImportance().orElse(goalToUpdate.getImportance());
        StartDateTime startDateTime = completeGoalDescriptor.getStartDateTime().orElse(goalToUpdate.getStartDateTime());
        EndDateTime updatedEndDateTime = completeGoalDescriptor.getEndDateTime()
                .orElse(goalToUpdate.getEndDateTime());
        Completion updatedCompletion = completeGoalDescriptor.getCompletion().orElse(goalToUpdate.getCompletion());

        return new Goal(importance, goalText, startDateTime, updatedEndDateTime, updatedCompletion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompleteGoalCommand)) {
            return false;
        }

        // state check
        CompleteGoalCommand e = (CompleteGoalCommand) other;
        return index.equals(e.index)
                && completeGoalDescriptor.equals(e.completeGoalDescriptor)
                && Objects.equals(goalToUpdate, e.goalToUpdate);
    }

    /**
     * Stores the details to update the goal with.
     */
    public static class CompleteGoalDescriptor {
        private GoalText GoalText;
        private Importance Importance;
        private StartDateTime StartDateTime;
        private EndDateTime EndDateTime;
        private Completion Completion;

        public CompleteGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public CompleteGoalDescriptor(CompleteGoalDescriptor toCopy) {
            setEndDateTime(toCopy.EndDateTime);
            setCompletion(toCopy.Completion);
        }

        public void setEndDateTime(EndDateTime EndDateTime) {
            this.EndDateTime = EndDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(EndDateTime);
        }

        public void setCompletion(Completion Completion) {
            this.Completion = Completion;
        }

        public Optional<Completion> getCompletion() {
            return Optional.ofNullable(Completion);
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(StartDateTime);
        }
        public Optional<Importance> getImportance() {
            return Optional.ofNullable(Importance);
        }
        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(GoalText);
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof CompleteGoalDescriptor)) {
                return false;
            }

            // state check
            CompleteGoalDescriptor e = (CompleteGoalDescriptor) other;

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getCompletion().equals(e.getCompletion());
        }
    }
}
