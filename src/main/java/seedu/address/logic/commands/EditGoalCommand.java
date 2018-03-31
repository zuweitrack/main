package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.goal.exceptions.GoalNotFoundException;

//@@author deborahlow97
/**
 * Edits the details of an existing goal in the address book.
 */
public class EditGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "~goal";
    public static final String COMMAND_ALIAS_1 = "~g";
    public static final String COMMAND_ALIAS_2 = "editgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the goal identified "
            + "by the index number used in the last goal listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GOAL_TEXT + "GOAL TEXT] "
            + "[" + PREFIX_IMPORTANCE + "IMPORTANCE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_IMPORTANCE + "2 ";

    public static final String MESSAGE_EDIT_GOAL_SUCCESS = "Edited Goal: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GOAL = "This goal already exists in the address book.";

    private final Index index;
    private final EditGoalDescriptor editGoalDescriptor;

    private Goal goalToEdit;
    private Goal editedGoal;

    /**
     * @param index of the goal in the filtered goal list to edit
     * @param editGoalDescriptor details to edit the goal with
     */
    public EditGoalCommand(Index index, EditGoalDescriptor editGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(editGoalDescriptor);

        this.index = index;
        this.editGoalDescriptor = new EditGoalDescriptor(editGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoal(goalToEdit, editedGoal);
        } catch (DuplicateGoalException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_GOAL);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_EDIT_GOAL_SUCCESS, editedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToEdit = lastShownList.get(index.getZeroBased());
        editedGoal = createEditedGoal(goalToEdit, editGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToEdit}
     * edited with {@code editGoalDescriptor}.
     */
    private static Goal createEditedGoal(Goal goalToEdit, EditGoalDescriptor editGoalDescriptor) {
        assert goalToEdit != null;

        GoalText updatedGoalText = editGoalDescriptor.getGoalText().orElse(goalToEdit.getGoalText());
        Importance updatedImportance = editGoalDescriptor.getImportance().orElse(goalToEdit.getImportance());
        StartDateTime startDateTime = goalToEdit.getStartDateTime();
        EndDateTime endDateTime = goalToEdit.getEndDateTime();
        Completion completion = goalToEdit.getCompletion();
        return new Goal(updatedImportance, updatedGoalText, startDateTime, endDateTime, completion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGoalCommand)) {
            return false;
        }

        // state check
        EditGoalCommand e = (EditGoalCommand) other;
        return index.equals(e.index)
                && editGoalDescriptor.equals(e.editGoalDescriptor)
                && Objects.equals(goalToEdit, e.goalToEdit);
    }

    /**
     * Stores the details to edit the goal with. Each non-empty field value will replace the
     * corresponding field value of the goal.
     */
    public static class EditGoalDescriptor {
        private GoalText goalText;
        private Importance importance;

        public EditGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGoalDescriptor(EditGoalDescriptor toCopy) {
            setGoalText(toCopy.goalText);
            setImportance(toCopy.importance);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.goalText, this.importance);
        }

        public void setGoalText(GoalText goalText) {
            this.goalText = goalText;
        }

        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(goalText);
        }

        public void setImportance(Importance importance) {
            this.importance = importance;
        }

        public Optional<Importance> getImportance() {
            return Optional.ofNullable(importance);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGoalDescriptor)) {
                return false;
            }

            // state check
            EditGoalDescriptor e = (EditGoalDescriptor) other;

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance());
        }
    }
}

