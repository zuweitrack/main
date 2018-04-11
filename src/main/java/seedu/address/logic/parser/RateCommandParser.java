package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL_OF_FRIENDSHIP;

import java.util.ArrayList;
import java.util.List;

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
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse (String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LEVEL_OF_FRIENDSHIP);

        String preamble;
        String[] indexStr;
        List<Index> indexList = new ArrayList();

        try {
            preamble = argumentMultimap.getPreamble();
            indexStr = preamble.split("\\s+");
            for (String index : indexStr) {
                indexList.add(ParserUtil.parseIndex(index));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RateCommand.MESSAGE_USAGE));
        }


        String levelOfFriendship = argumentMultimap.getValue(PREFIX_LEVEL_OF_FRIENDSHIP).get();

        return new RateCommand(indexList, new String(levelOfFriendship));
    }

}
