package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new SortGoalCommand object
 */
public class SortGoalCommandParser implements Parser<SortGoalCommand> {

    @Override
    public SortGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_FIELD, PREFIX_SORT_ORDER);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_FIELD, PREFIX_SORT_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE));
        }
        try {
            String sortField = ParserUtil.parseSortGoalField(argMultimap.getValue(PREFIX_SORT_FIELD)).get();
            String sortOrder = ParserUtil.parseSortGoalOrder(argMultimap.getValue(PREFIX_SORT_ORDER)).get();
            return new SortGoalCommand(sortField, sortOrder);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE));
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
