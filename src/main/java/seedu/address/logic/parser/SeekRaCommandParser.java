package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SeekRaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.UnitNumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SeekRaCommand object
 */
public class SeekRaCommandParser implements Parser<SeekRaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SeekRaCommand
     * and returns an SeekRaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SeekRaCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeekRaCommand.MESSAGE_USAGE));
        }

        trimmedArgs = trimmedArgs + " " + "RA";

        String[] nameKeywords = (trimmedArgs.split("\\s+"));

        return new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
