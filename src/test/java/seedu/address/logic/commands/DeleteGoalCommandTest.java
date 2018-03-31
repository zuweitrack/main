package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalGoals.getTypicalGoalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GOAL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.Goal;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGoalCommand}.
 */
public class DeleteGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Goal goalToDelete = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        DeleteGoalCommand deleteGoalCommand = prepareCommand(INDEX_FIRST_GOAL);

        String expectedMessage = String.format(DeleteGoalCommand.MESSAGE_DELETE_GOAL_SUCCESS, goalToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGoal(goalToDelete);

        assertCommandSuccess(deleteGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        DeleteGoalCommand deleteGoalCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal goalToDelete = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        DeleteGoalCommand deleteGoalCommand = prepareCommand(INDEX_FIRST_GOAL);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first goal deleted
        deleteGoalCommand.execute();
        undoRedoStack.push(deleteGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal deleted again
        expectedModel.deleteGoal(goalToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        DeleteGoalCommand deleteGoalCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteGoalCommand not pushed into undoRedoStack
        assertCommandFailure(deleteGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteGoalCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_GOAL);
        DeleteGoalCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_GOAL);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGoalCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_GOAL);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different goal -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteGoalCommand} with the parameter {@code index}.
     */
    private DeleteGoalCommand prepareCommand(Index index) {
        DeleteGoalCommand deleteGoalCommand = new DeleteGoalCommand(index);
        deleteGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGoalCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoGoal(Model model) {
        model.updateFilteredGoalList(p -> false);

        assertTrue(model.getFilteredGoalList().isEmpty());
    }
}
