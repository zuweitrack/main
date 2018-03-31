package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
//import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
//import seedu.address.logic.commands.AddCommandTest.ModelStub;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.testutil.GoalBuilder;

//@@author deborahlow97
public class AddGoalCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullGoal_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGoalCommand(null);
    }

    /*@Test
    public void execute_goalAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGoalAdded modelStub = new ModelStubAcceptingGoalAdded();
        Goal validGoal = new GoalBuilder().build();

        CommandResult commandResult = getAddGoalCommandForGoal(validGoal, modelStub).execute();

        assertEquals(String.format(AddGoalCommand.MESSAGE_SUCCESS, validGoal), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGoal), modelStub.goalsAdded);
    }

    @Test
    public void execute_duplicateGoal_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateGoalException();
        Goal validGoal = new GoalBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddGoalCommand.MESSAGE_DUPLICATE_GOAL);

        getAddGoalCommandForGoal(validGoal, modelStub).execute();
    }
    */

    @Test
    public void equals() {
        Goal a = new GoalBuilder().withGoalText("A").build();
        Goal b = new GoalBuilder().withGoalText("B").build();
        AddGoalCommand addGoalACommand = new AddGoalCommand(a);
        AddGoalCommand addGoalBCommand = new AddGoalCommand(b);

        // same object -> returns true
        assertTrue(addGoalACommand.equals(addGoalACommand));

        // same values -> returns true
        AddGoalCommand addGoalACommandCopy = new AddGoalCommand(a);
        assertTrue(addGoalACommand.equals(addGoalACommandCopy));

        // different types -> returns false
        assertFalse(addGoalACommand.equals(1));

        // null -> returns false
        assertFalse(addGoalACommand.equals(null));

        // different goal -> returns false
        assertFalse(addGoalACommand.equals(addGoalBCommand));
    }

    /**
     * Generates a new AddGoalCommand with the details of the given goal.
     */
    private AddGoalCommand getAddGoalCommandForGoal(Goal goal, Model model) {
        AddGoalCommand command = new AddGoalCommand(goal);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateGoalException when trying to add a goal.
     */
    private class ModelStubThrowingDuplicateGoalException extends AddCommandTest.ModelStub {
        @Override
        public void addGoal(Goal goal) throws DuplicateGoalException {
            throw new DuplicateGoalException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the goal being added.
     */
    private class ModelStubAcceptingGoalAdded extends AddCommandTest.ModelStub {
        final ArrayList<Goal> goalsAdded = new ArrayList<>();

        @Override
        public void addGoal(Goal goal) throws DuplicateGoalException {
            requireNonNull(goal);
            goalsAdded.add(goal);
        }
    }
}
