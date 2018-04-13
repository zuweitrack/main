package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL_OF_FRIENDSHIP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author zuweitrack
/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser {

    /**
     * Returns true if the level of friendship prefix "/*" is present
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(groupPrefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the level of friendship is between 1 - 10
     */
    private static boolean containsValidRange(String levelOfFriendship) {
        return levelOfFriendship.matches("0?[1-9]|[1][0]");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse (String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LEVEL_OF_FRIENDSHIP);

        if (!isPrefixesPresent(argumentMultimap, PREFIX_LEVEL_OF_FRIENDSHIP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        String preamble;
        String[] indexString;
        List<Index> indexList = new ArrayList<>();

        try {
            preamble = argumentMultimap.getPreamble();
            indexString = preamble.split("\\s+");
            for (String index : indexString) {
                indexList.add(ParserUtil.parseIndex(index));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RateCommand.MESSAGE_USAGE));
        }


        String levelOfFriendship = argumentMultimap.getValue(PREFIX_LEVEL_OF_FRIENDSHIP).get();

        if (!containsValidRange(levelOfFriendship)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        return new RateCommand(indexList, new String(levelOfFriendship));
    }

}
