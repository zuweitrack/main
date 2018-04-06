package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TEXT;
import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;
import static seedu.address.logic.parser.DateTimeParser.properReminderDateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.ReminderTextPredicate;

//@@author fuadsahmawi
/**
 * Parses input arguments and creates a new DeleteReminderCommand object
 */
public class DeleteReminderCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns an DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_TEXT, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_TEXT, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }

        String reminderText = argMultimap.getValue(PREFIX_REMINDER_TEXT).get();
        String dateTime = argMultimap.getValue(PREFIX_DATE).get();
        LocalDateTime localDateTime = nattyDateAndTimeParser(dateTime).get();
        dateTime = properReminderDateTimeFormat(localDateTime);
        String trimmedArgs = reminderText.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new DeleteReminderCommand(new ReminderTextPredicate(Arrays.asList(nameKeywords)), dateTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
