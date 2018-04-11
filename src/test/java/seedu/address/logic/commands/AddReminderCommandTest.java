package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.testutil.ReminderBuilder;

//@@author fuadsahmawi
public class AddReminderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }

    /*
    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddReminderCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        getAddReminderCommandForReminder(validReminder, modelStub).execute();
    }
    */

    @Test
    public void equals() {
        Reminder a = new ReminderBuilder().withReminderText("A").build();
        Reminder b = new ReminderBuilder().withReminderText("B").build();
        AddReminderCommand addReminderACommand = new AddReminderCommand(a);
        AddReminderCommand addReminderBCommand = new AddReminderCommand(b);

        // same object -> returns true
        assertTrue(addReminderACommand.equals(addReminderACommand));

        // same values -> returns true
        AddReminderCommand addReminderACommandCopy = new AddReminderCommand(a);
        assertTrue(addReminderACommand.equals(addReminderACommandCopy));

        // different types -> returns false
        assertFalse(addReminderACommand.equals(1));

        // null -> returns false
        assertFalse(addReminderACommand.equals(null));

        // different reminder -> returns false
        assertFalse(addReminderACommand.equals(addReminderBCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminderCommand getAddReminderCommandForReminder(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateReminderException when trying to add a reminder.
     */
    private class ModelStubThrowingDuplicateReminderException extends AddCommandTest.ModelStub {
        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends AddCommandTest.ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }
    }
}
