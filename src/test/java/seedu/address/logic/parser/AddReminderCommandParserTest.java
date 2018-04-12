package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.ReminderCommandTestUtil.INVALID_REMINDER_TEXT_DESC;
import static seedu.address.logic.commands.ReminderCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.ReminderCommandTestUtil.REMINDER_END_DATE_TIME_DESC_A;
import static seedu.address.logic.commands.ReminderCommandTestUtil.REMINDER_END_DATE_TIME_DESC_B;
import static seedu.address.logic.commands.ReminderCommandTestUtil.REMINDER_START_DATE_TIME_DESC_A;
import static seedu.address.logic.commands.ReminderCommandTestUtil.REMINDER_START_DATE_TIME_DESC_B;
import static seedu.address.logic.commands.ReminderCommandTestUtil.REMINDER_TEXT_DESC_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddReminderCommand;

//@@author fuadsahmawi
public class AddReminderCommandParserTest {
    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing end date time prefix
        assertParseFailure(parser, REMINDER_TEXT_DESC_B + REMINDER_START_DATE_TIME_DESC_B, expectedMessage);

        // missing reminder text prefix
        assertParseFailure(parser, REMINDER_START_DATE_TIME_DESC_B + REMINDER_END_DATE_TIME_DESC_B,
                expectedMessage);

        // missing start date time prefix
        assertParseFailure(parser, REMINDER_TEXT_DESC_B + REMINDER_END_DATE_TIME_DESC_B,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid goal text
        assertParseFailure(parser, INVALID_REMINDER_TEXT_DESC + REMINDER_START_DATE_TIME_DESC_A
                + REMINDER_END_DATE_TIME_DESC_A,
                String.format(MESSAGE_INVALID_DATE_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + REMINDER_TEXT_DESC_B + REMINDER_START_DATE_TIME_DESC_B
                + REMINDER_END_DATE_TIME_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
    }
}
