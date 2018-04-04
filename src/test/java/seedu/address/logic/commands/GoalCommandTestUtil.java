package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.DateTimeParser.getLocalDateTimeFromString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.GoalNotFoundException;
import seedu.address.testutil.CompleteGoalDescriptorBuilder;
import seedu.address.testutil.EditGoalDescriptorBuilder;


//@@author deborahlow97
/**
 * Contains helper methods for testing commands.
 */
public class GoalCommandTestUtil {

    public static final String VALID_GOAL_TEXT_A = "Make 10 new friends in university";
    public static final String VALID_GOAL_TEXT_B = "Drink 8 glasses of water everyday - stay hydrated!!";
    public static final String VALID_GOAL_IMPORTANCE_A = "1";
    public static final String VALID_GOAL_IMPORTANCE_B = "10";
    public static final String VALID_GOAL_START_DATE_TIME_STRING_A = "2018-03-03 10:30";
    public static final String VALID_GOAL_START_DATE_TIME_STRING_B = "2018-03-03 10:31";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_A = "2018-04-04 10:30";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_B = "";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_C = "14/10/2018 3pm";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_D = "10/10/2018 4pm";
    public static final boolean VALID_GOAL_COMPLETION_A = true;
    public static final boolean VALID_GOAL_COMPLETION_B = false;
    public static final boolean VALID_GOAL_COMPLETION_C = true;
    public static final boolean VALID_GOAL_COMPLETION_D = true;
    public static final String GOAL_TEXT_DESC_A = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_A;
    public static final String GOAL_TEXT_DESC_B = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_B;
    public static final String GOAL_IMPORTANCE_DESC_A = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_A;
    public static final String GOAL_IMPORTANCE_DESC_B = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_B;

    public static final String INVALID_IMPORTANCE_DESC = " " + PREFIX_IMPORTANCE + "-1";
    // negative numbers not allowed in importance

    public static final String INVALID_GOAL_TEXT_DESC = " " + PREFIX_GOAL_TEXT + "";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_A;
    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_B;
    public static final EditGoalCommand.EditGoalDescriptor DESC_GOAL_A;
    public static final EditGoalCommand.EditGoalDescriptor DESC_GOAL_B;

    public static final CompleteGoalCommand.CompleteGoalDescriptor DESC_GOAL_COMPLETED_C;
    public static final CompleteGoalCommand.CompleteGoalDescriptor DESC_GOAL_COMPLETED_D;

    static {
        VALID_GOAL_START_DATE_TIME_A = getLocalDateTimeFromString(VALID_GOAL_START_DATE_TIME_STRING_A);
        VALID_GOAL_START_DATE_TIME_B = getLocalDateTimeFromString(VALID_GOAL_START_DATE_TIME_STRING_B);

        DESC_GOAL_A = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_A)
                .withGoalText(VALID_GOAL_TEXT_A).build();
        DESC_GOAL_B = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        DESC_GOAL_COMPLETED_C = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_C)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_C).build();
        DESC_GOAL_COMPLETED_D = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered goal list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Goal> expectedFilteredList = new ArrayList<>(actualModel.getFilteredGoalList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredGoalList());
        }
    }

    /**
     * Deletes the first GOAL in {@code model}'s list from {@code model}'s address book.
     */
    public static void deleteFirstGoal(Model model) {
        Goal firstGoal = model.getFilteredGoalList().get(0);
        try {
            model.deleteGoal(firstGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("Goal in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
