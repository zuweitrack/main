package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.MeetCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Meet;


/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class MeetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String date = "14/03/2018";

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, date), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), date));
    }

    @Test
    public void equals() {
        String testDate = "15/03/2018";
        String testDateTwo = "16/03/2018";
        final MeetCommand standardCommand = new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDate));

        // same values -> returns true
        MeetCommand commandWithSameValues = new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDate));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(INDEX_SECOND_PERSON, new Meet(testDate))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDateTwo))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private MeetCommand prepareCommand(Index index, String date) {
        MeetCommand meetCommand = new MeetCommand(index, new Meet(date));
        meetCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return meetCommand;
    }
}

