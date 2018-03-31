package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.GoalCommandTestUtil.DESC_GOAL_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.DESC_GOAL_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_B;
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
import seedu.address.logic.commands.EditGoalCommand.EditGoalDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.Goal;
import seedu.address.testutil.EditGoalDescriptorBuilder;
import seedu.address.testutil.GoalBuilder;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditGoalCommand.
 */
public class EditGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Goal editedGoal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(editedGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoal(model.getFilteredGoalList().get(0), editedGoal);

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastGoal = Index.fromOneBased(model.getFilteredGoalList().size());
        Goal lastGoal = model.getFilteredGoalList().get(indexLastGoal.getZeroBased());

        GoalBuilder goalInList = new GoalBuilder(lastGoal);
        Goal editedGoal = goalInList.withGoalText(VALID_GOAL_TEXT_B).withImportance(VALID_GOAL_IMPORTANCE_B).build();

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(indexLastGoal, descriptor);

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoal(lastGoal, editedGoal);

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, new EditGoalDescriptor());
        Goal editedGoal = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGoalUnfilteredList_failure() {
        Goal firstGoal = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(firstGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_SECOND_GOAL, descriptor);

        assertCommandFailure(editGoalCommand, model, EditGoalCommand.MESSAGE_DUPLICATE_GOAL);
    }

    @Test
    public void execute_invalidGoalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal editedGoal = new GoalBuilder().build();
        Goal goalToEdit = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(editedGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first goal edited
        editGoalCommand.execute();
        undoRedoStack.push(editGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal edited again
        expectedModel.updateGoal(goalToEdit, editedGoal);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editGoalCommand not pushed into undoRedoStack
        assertCommandFailure(editGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final EditGoalCommand standardCommand = prepareCommand(INDEX_FIRST_GOAL, DESC_GOAL_A);

        // same values -> returns true
        EditGoalDescriptor copyDescriptor = new EditGoalDescriptor(DESC_GOAL_A);
        EditGoalCommand commandWithSameValues = prepareCommand(INDEX_FIRST_GOAL, copyDescriptor);
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
        assertFalse(standardCommand.equals(new EditGoalCommand(INDEX_SECOND_GOAL, DESC_GOAL_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditGoalCommand(INDEX_FIRST_GOAL, DESC_GOAL_B)));
    }

    /**
     * Returns an {@code EditGoalCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditGoalCommand prepareCommand(Index index, EditGoalDescriptor descriptor) {
        EditGoalCommand editGoalCommand = new EditGoalCommand(index, descriptor);
        editGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editGoalCommand;
    }
}
