package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_BIRTHDAY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_LEVEL_OF_FRIENDSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_MEET_DATE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;




//@@author sham-sheer
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index sortType = INDEX_SORT_LEVEL_OF_FRIENDSHIP;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList2_success() throws Exception {
        Index sortType = INDEX_SORT_MEET_DATE;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_MEET_DATE, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList3_success() throws Exception {
        Index sortType = INDEX_SORT_BIRTHDAY;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_BIRTHDAY, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(4);
        SortCommand sortCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(sortCommand, model, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    private SortCommand prepareCommand(Index index) {
        SortCommand sortCommand = new SortCommand(index);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
