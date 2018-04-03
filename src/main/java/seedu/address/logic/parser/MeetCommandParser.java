package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.MeetCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Meet;






/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class MeetCommandParser implements Parser {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MeetCommand}
     * and returns a {@code MeetCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MeetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Meet meetDate = ParserUtil.parseMeetDate(argMultimap.getValue(PREFIX_DATE)).get();
            return new MeetCommand(index, meetDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
