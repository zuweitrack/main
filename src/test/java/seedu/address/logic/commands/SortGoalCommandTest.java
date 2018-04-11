package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortGoalCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalGoals.getTypicalGoalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.exceptions.EmptyGoalListException;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model) and unit tests for Sort Goal Command.
 */
public class SortGoalCommandTest {

    private static final String VALID_GOAL_FIELD = "importance";
    private static final String VALID_GOAL_ORDER = "descending";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private SortGoalCommand sortGoalCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortGoalCommand = new SortGoalCommand(VALID_GOAL_FIELD, VALID_GOAL_ORDER);
        sortGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }


    @Test
    public void execute_goalListIsNotFiltered_showsSortedList() {
        assertCommandSuccess(sortGoalCommand, model, String.format(MESSAGE_SUCCESS, VALID_GOAL_FIELD,
                VALID_GOAL_ORDER), expectedModel);
    }

    @Test
    public void execute_emptyGoalList_throwsCommandException() throws Exception {
        AddCommandTest.ModelStub modelStub = new ModelStubThrowingEmptyGoalListException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_SORT_COMMAND_USAGE);

        getSortGoalCommandForGoal(VALID_GOAL_FIELD, VALID_GOAL_ORDER, modelStub).execute();
    }

    /**
     * Generates a new SortGoalCommand with the details of the given goal.
     */
    private SortGoalCommand getSortGoalCommandForGoal(String goalField, String goalOrder, Model model) {
        SortGoalCommand command = new SortGoalCommand(goalField, goalOrder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * A Model stub that always throw a EmptyGoalListException when trying to sort goal list.
     */
    private class ModelStubThrowingEmptyGoalListException extends AddCommandTest.ModelStub {
        @Override
        public void sortGoal(String goalField, String goalOrder) throws EmptyGoalListException {
            throw new EmptyGoalListException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
