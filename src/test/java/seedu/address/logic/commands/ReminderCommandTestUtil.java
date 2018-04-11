package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TEXT;
import static seedu.address.logic.parser.DateTimeParser.getLocalDateTimeFromString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

//@@author fuadsahmawi
/**
 * Contains helper methods for testing reminder commands.
 */
public class ReminderCommandTestUtil {
    public static final String VALID_REMINDER_TEXT_A = "Medical Appointment";
    public static final String VALID_REMINDER_TEXT_B = "CG2271 Finals";
    public static final String VALID_REMINDER_START_DATE_TIME_STRING_A = "2018-03-03 10:30";
    public static final String VALID_REMINDER_START_DATE_TIME_STRING_B = "2018-03-03 10:31";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_A = "2018-03-03 12:30";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_B = "";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_C = "14/10/2018 3pm";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_D = "10/10/2018 4pm";
    public static final String REMINDER_TEXT_DESC_A = " " + PREFIX_REMINDER_TEXT + VALID_REMINDER_TEXT_A;
    public static final String REMINDER_TEXT_DESC_B = " " + PREFIX_REMINDER_TEXT + VALID_REMINDER_TEXT_B;
    public static final String REMINDER_START_DATE_TIME_DESC_A = " " + PREFIX_DATE
            + VALID_REMINDER_START_DATE_TIME_STRING_A;
    public static final String REMINDER_START_DATE_TIME_DESC_B = " " + PREFIX_DATE
            + VALID_REMINDER_START_DATE_TIME_STRING_B;
    public static final String REMINDER_END_DATE_TIME_DESC_A = " " + PREFIX_END_DATE
            + VALID_REMINDER_END_DATE_TIME_STRING_A;
    public static final String REMINDER_END_DATE_TIME_DESC_B = " " + PREFIX_END_DATE
            + VALID_REMINDER_END_DATE_TIME_STRING_B;

    public static final String INVALID_REMINDER_TEXT_DESC = " " + PREFIX_REMINDER_TEXT + "";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final LocalDateTime VALID_REMINDER_START_DATE_TIME_A;
    public static final LocalDateTime VALID_REMINDER_START_DATE_TIME_B;


    static {
        VALID_REMINDER_START_DATE_TIME_A = getLocalDateTimeFromString(VALID_REMINDER_START_DATE_TIME_STRING_A);
        VALID_REMINDER_START_DATE_TIME_B = getLocalDateTimeFromString(VALID_REMINDER_START_DATE_TIME_STRING_B);
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
     * - the address book and the filtered REMINDER list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Reminder> expectedFilteredList = new ArrayList<>(actualModel.getFilteredReminderList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredReminderList());
        }
    }

    /**
     * Deletes the first REMINDER in {@code model}'s list from {@code model}'s address book.
     */
    public static void deleteFirstReminder(Model model) {
        Reminder firstReminder = model.getFilteredReminderList().get(0);
        try {
            model.deleteReminder(firstReminder);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("Reminder in filtered list must exist in model.", pnfe);
        }
    }
}
