package seedu.address.logic.commands;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.GoalNotFoundException;
import seedu.address.model.util.SampleGoalDataUtil;
import seedu.address.testutil.EditGoalDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class GoalCommandTestUtil {

    public static final String VALID_GOAL_TEXT_A = "Make 10 new friends in university";
    public static final String VALID_GOAL_TEXT_B = "Drink 8 glasses of water everyday - stay hydrated!!";
    public static final String VALID_GOAL_IMPORTANCE_A = "1";
    public static final String VALID_GOAL_IMPORTANCE_B = "2";
    public static final String VALID_GOAL_START_DATE_TIME_STRING_A = 

    public static final String GOAL_TEXT_DESC_A = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_A;
    public static final String GOAL_TEXT_DESC_B = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_B;
    public static final String GOAL_IMPORTANCE_DESC_A = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_A;
    public static final String GOAL_IMPORTANCE_DESC_B = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_B;

    public static final String INVALID_IMPORTANCE_DESC = " " + PREFIX_IMPORTANCE + "-1";
    // negative numbers not allowed in importance

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_A;
    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_B;
    public static final LocalDateTime VALID_GOAL_END_DATE_TIME_A;
    public static final LocalDateTime VALID_GOAL_END_DATE_TIME_B;
    public static final EditCommand.EditGoalDescriptor DESC_GOAL_A;
    public static final EditCommand.EditGoalDescriptor DESC_GOAL_B;

    static {
        DESC_GOAL_A = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_A)
                .withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        DESC_GOAL_B = new EditGoalDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB).withUnitNumber(VALID_UNIT_NUMBER_BOB)
                .withCcas(VALID_CCA_BADMINTON, VALID_CCA_DANCE).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showGoalTextAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGoalList().size());

        Goal goal = model.getFilteredGoalList().get(targetIndex.getZeroBased());
        final String[] splitName = goal.getGoalText().split("\\s+");
        model.updateFilteredGoalList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredGoalList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
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
