package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.GoalCommandTestUtil.DESC_GOAL_COMPLETED_C;
import static seedu.address.logic.commands.GoalCommandTestUtil.DESC_GOAL_COMPLETED_D;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_D;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_D;
import static seedu.address.logic.commands.GoalCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GoalCommandTestUtil.assertCommandSuccess;
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
import seedu.address.logic.commands.CompleteGoalCommand.CompleteGoalDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.Goal;
import seedu.address.testutil.CompleteGoalDescriptorBuilder;
import seedu.address.testutil.GoalBuilder;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * CompleteGoalCommand.
 */
public class CompleteGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_allPreSpecifiedFieldsUnfilteredList_success() throws Exception {
        Index indexLastGoal = Index.fromOneBased(model.getFilteredGoalList().size());
        Goal lastGoal = model.getFilteredGoalList().get(indexLastGoal.getZeroBased());

        GoalBuilder goalInList = new GoalBuilder(lastGoal);
        Goal completedGoal = goalInList.withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();

        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(indexLastGoal, descriptor);

        String expectedMessage = String.format(CompleteGoalCommand.MESSAGE_COMPLETE_GOAL_SUCCESS, completedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoalWithoutParameters(lastGoal, completedGoal);

        assertCommandSuccess(completeGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGoalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(completeGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal completedGoal = new GoalBuilder().build();
        Goal goalToEdit = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(completedGoal).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first goal completed
        completeGoalCommand.execute();
        undoRedoStack.push(completeGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal completed again
        expectedModel.updateGoalWithoutParameters(goalToEdit, completedGoal);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> completeGoalCommand not pushed into undoRedoStack
        assertCommandFailure(completeGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final CompleteGoalCommand standardCommand = prepareCommand(INDEX_FIRST_GOAL, DESC_GOAL_COMPLETED_C);

        // same values -> returns true
        CompleteGoalDescriptor copyDescriptor = new CompleteGoalDescriptor(DESC_GOAL_COMPLETED_C);
        CompleteGoalCommand commandWithSameValues = prepareCommand(INDEX_FIRST_GOAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CompleteGoalCommand(INDEX_SECOND_GOAL, DESC_GOAL_COMPLETED_C)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CompleteGoalCommand(INDEX_FIRST_GOAL, DESC_GOAL_COMPLETED_D)));
    }

    /**
     * Returns an {@code CompleteGoalCommand} with parameters {@code index} and {@code descriptor}
     */
    private CompleteGoalCommand prepareCommand(Index index, CompleteGoalDescriptor descriptor) {
        CompleteGoalCommand completeGoalCommand = new CompleteGoalCommand(index, descriptor);
        completeGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return completeGoalCommand;
    }
}

