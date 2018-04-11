package seedu.address.logic.commands;

import static seedu.address.logic.commands.ReminderCommandTestUtil.VALID_REMINDER_TEXT_B;
import static seedu.address.logic.commands.ReminderCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ReminderCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

//@@author fuadsahmawi
/**
 * Contains integration tests (interaction with the Model) for {@code AddReminderCommand}.
 */
public class AddReminderCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalReminderAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() throws Exception {
        Reminder validReminder = new ReminderBuilder().withReminderText(VALID_REMINDER_TEXT_B).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReminder(validReminder);

        assertCommandSuccess(prepareCommand(validReminder, model), model,
                String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder reminderInList = model.getAddressBook().getReminderList().get(0);
        assertCommandFailure(prepareCommand(reminderInList, model),
                model, AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    /**
     * Generates a new {@code AddRemminderCommand} which upon execution, adds {@code reminder} into the {@code model}.
     */
    private AddReminderCommand prepareCommand(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
