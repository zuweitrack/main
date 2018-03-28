package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_RA_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.UnitNumberContainsKeywordsPredicate;
/**
 * Contains integration tests (interaction with the Model) for {@code SeekRaCommand}.
 */
public class SeekRaCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        UnitNumberContainsKeywordsPredicate firstPredicate =
                new UnitNumberContainsKeywordsPredicate(Collections.singletonList("first"));
        UnitNumberContainsKeywordsPredicate secondPredicate =
                new UnitNumberContainsKeywordsPredicate(Collections.singletonList("second"));

        SeekRaCommand seekRaFirstCommand = new SeekRaCommand(firstPredicate);
        SeekRaCommand seekRaSecondCommand = new SeekRaCommand(secondPredicate);

        // same object -> returns true
        assertTrue(seekRaFirstCommand.equals(seekRaFirstCommand));

        // same values -> returns true
        SeekRaCommand seekRaFirstCommandCopy = new SeekRaCommand(firstPredicate);
        assertTrue(seekRaFirstCommand.equals(seekRaFirstCommandCopy));

        // different types -> returns false
        assertFalse(seekRaFirstCommand.equals(1));

        // null -> returns false
        assertFalse(seekRaFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(seekRaFirstCommand.equals(seekRaSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 0);
        SeekRaCommand command = prepareNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 3);
        SeekRaCommand command = prepareNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 7);
        SeekRaCommand command = prepareTagCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code SeekRaCommand}.
     */
    private SeekRaCommand prepareNameCommand(String userInput) {
        SeekRaCommand command =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code SeekRaCommand}.
     */
    private SeekRaCommand prepareTagCommand(String userInput) {
        SeekRaCommand command =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SeekRaCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
