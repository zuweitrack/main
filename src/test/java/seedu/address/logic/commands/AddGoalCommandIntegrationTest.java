package seedu.address.logic.commands;

import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GoalCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoals.getTypicalGoalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goal.Goal;
import seedu.address.testutil.GoalBuilder;

//@@author deborahlow97
/**
 * Contains integration tests (interaction with the Model) for {@code AddGoalCommand}.
 */
public class AddGoalCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGoal_success() throws Exception {
        Goal validGoal = new GoalBuilder().withImportance(VALID_GOAL_IMPORTANCE_B).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGoal(validGoal);

        assertCommandSuccess(prepareCommand(validGoal, model), model,
                String.format(AddGoalCommand.MESSAGE_SUCCESS, validGoal), expectedModel);
    }

    @Test
    public void execute_duplicateGoal_throwsCommandException() {
        Goal goalInList = model.getAddressBook().getGoalList().get(0);
        assertCommandFailure(prepareCommand(goalInList, model), model, AddGoalCommand.MESSAGE_DUPLICATE_GOAL);
    }

    /**
     * Generates a new {@code AddGoalCommand} which upon execution, adds {@code goal} into the {@code model}.
     */
    private AddGoalCommand prepareCommand(Goal goal, Model model) {
        AddGoalCommand command = new AddGoalCommand(goal);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

