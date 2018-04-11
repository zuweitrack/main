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
import seedu.address.model.goal.exceptions.OngoingGoalException;

//@@author deborahlow97
/**
 * Edits the details of an existing goal in the address book.
 */
public class OngoingGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "-!goal";
    public static final String COMMAND_ALIAS_1 = "-!g";
    public static final String COMMAND_ALIAS_2 = "ongoinggoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate identified goal is not completed "
            + "and still ongoing. Goal is identified "
            + "by the index number used in the last goal listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ONGOING_GOAL_SUCCESS = "Ongoing Goal! : %1$s";

    private final Index index;
    private final OngoingGoalDescriptor ongoingGoalDescriptor;

    private Goal goalToUpdate;
    private Goal updatedGoal;

    /**
     * @param index of the goal in the filtered goal list to update
     */
    public OngoingGoalCommand(Index index, OngoingGoalDescriptor ongoingGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(ongoingGoalDescriptor);

        this.index = index;
        this.ongoingGoalDescriptor = new OngoingGoalDescriptor(ongoingGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoalWithoutParameters(goalToUpdate, updatedGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_ONGOING_GOAL_SUCCESS, updatedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToUpdate = lastShownList.get(index.getZeroBased());
        if (!goalToUpdate.getCompletion().hasCompleted) {
            throw new CommandException(Messages.MESSAGE_GOAL_ONGOING_ERROR);
        }
        updatedGoal = createUpdatedGoal(goalToUpdate, ongoingGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToUpdate}
     * edited with {@code ongoingGoalDescriptor}.
     */
    private static Goal createUpdatedGoal(Goal goalToUpdate, OngoingGoalDescriptor ongoingGoalDescriptor) {
        assert goalToUpdate != null;

        GoalText goalText = ongoingGoalDescriptor.getGoalText().orElse(goalToUpdate.getGoalText());
        Importance importance = ongoingGoalDescriptor.getImportance().orElse(goalToUpdate.getImportance());
        StartDateTime startDateTime = ongoingGoalDescriptor.getStartDateTime().orElse(goalToUpdate.getStartDateTime());
        EndDateTime updatedEndDateTime = ongoingGoalDescriptor.getEndDateTime()
                .orElse(goalToUpdate.getEndDateTime());
        Completion updatedCompletion = ongoingGoalDescriptor.getCompletion().orElse(goalToUpdate.getCompletion());

        return new Goal(importance, goalText, startDateTime, updatedEndDateTime, updatedCompletion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OngoingGoalCommand)) {
            return false;
        }

        // state check
        OngoingGoalCommand e = (OngoingGoalCommand) other;
        return index.equals(e.index)
                && ongoingGoalDescriptor.equals(e.ongoingGoalDescriptor)
                && Objects.equals(goalToUpdate, e.goalToUpdate);
    }

    /**
     * Stores the details to update the goal with.
     */
    public static class OngoingGoalDescriptor {
        private GoalText goalText;
        private Importance importance;
        private StartDateTime startDateTime;
        private EndDateTime endDateTime;
        private Completion completion;

        public OngoingGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public OngoingGoalDescriptor(OngoingGoalDescriptor toCopy) {
            setEndDateTime(toCopy.endDateTime);
            setCompletion(toCopy.completion);
        }

        public void setEndDateTime(EndDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setCompletion(Completion completion) {
            this.completion = completion;
        }

        public Optional<Completion> getCompletion() {
            return Optional.ofNullable(completion);
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public Optional<Importance> getImportance() {
            return Optional.ofNullable(importance);
        }
        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(goalText);
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof OngoingGoalDescriptor)) {
                return false;
            }

            // state check
            OngoingGoalDescriptor e = (OngoingGoalDescriptor) other;

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getCompletion().equals(e.getCompletion());
        }
    }
}
