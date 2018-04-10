package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TEXT;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderText;

//@@author fuadsahmawi
/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_TEXT, PREFIX_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_TEXT, PREFIX_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        try {
            ReminderText reminderText = ParserUtil.parseReminderText(argMultimap.getValue(PREFIX_REMINDER_TEXT)).get();
            DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE)).get();
            EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATE)).get();
            Reminder reminder = new Reminder(reminderText, dateTime, endDateTime);
            return new AddReminderCommand(reminder);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
