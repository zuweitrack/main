package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.GoalCommandTestUtil.DESC_GOAL_COMPLETED_E;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_E;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME;
import static seedu.address.logic.commands.GoalCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GoalCommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.GoalCommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalGoals.getTypicalGoalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GOAL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.OngoingGoalCommand.OngoingGoalDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.Goal;
import seedu.address.testutil.GoalBuilder;
import seedu.address.testutil.OngoingGoalDescriptorBuilder;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * OngoingGoalCommand.
 */
public class OngoingGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_goalAlreadyOngoingUnfilteredList_throwsCommandException() throws Exception {
        Index indexLastGoal = Index.fromOneBased(model.getFilteredGoalList().size());
        Goal lastGoal = model.getFilteredGoalList().get(indexLastGoal.getZeroBased());

        GoalBuilder goalInList = new GoalBuilder(lastGoal);
        Goal ongoingGoal = goalInList.withCompletion(VALID_GOAL_COMPLETION_E)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME).build();

        OngoingGoalDescriptor descriptor = new OngoingGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_E)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME).build();
        OngoingGoalCommand ongoingGoalCommand = prepareCommand(indexLastGoal, descriptor);

        String expectedCommandException = Messages.MESSAGE_GOAL_ONGOING_ERROR;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoalWithoutParameters(lastGoal, ongoingGoal);

        assertCommandFailure(ongoingGoalCommand, model, expectedCommandException);
    }

    @Test
    public void execute_invalidGoalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        OngoingGoalDescriptor descriptor = new OngoingGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_E)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME).build();
        OngoingGoalCommand ongoingGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(ongoingGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        OngoingGoalDescriptor descriptor = new OngoingGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_E)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME).build();
        OngoingGoalCommand ongoingGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> ongoingGoalCommand not pushed into undoRedoStack
        assertCommandFailure(ongoingGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final OngoingGoalCommand standardCommand = prepareCommand(INDEX_FIRST_GOAL, DESC_GOAL_COMPLETED_E);

        // same values -> returns true
        OngoingGoalDescriptor copyDescriptor = new OngoingGoalDescriptor(DESC_GOAL_COMPLETED_E);
        OngoingGoalCommand commandWithSameValues = prepareCommand(INDEX_FIRST_GOAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new OngoingGoalCommand(INDEX_SECOND_GOAL, DESC_GOAL_COMPLETED_E)));
    }

    /**
     * Returns an {@code OngoingGoalCommand} with parameters {@code index} and {@code descriptor}
     */
    private OngoingGoalCommand prepareCommand(Index index, OngoingGoalDescriptor descriptor) {
        OngoingGoalCommand ongoingGoalCommand = new OngoingGoalCommand(index, descriptor);
        ongoingGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ongoingGoalCommand;
    }
}
