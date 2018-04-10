package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortGoalCommand object
 */
public class SortGoalCommandParser implements Parser<SortGoalCommand> {

    @Override
    public SortGoalCommand parse(String args) throws ParseException {
        try {
            String sortField = ParserUtil.parseSortGoalField(args);
            return new SortGoalCommand(sortField);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE));
        }
    }
}
