package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new DeleteGoalCommand object
 */
public class DeleteGoalCommandParser implements Parser<DeleteGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoalCommand
     * and returns an DeleteGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGoalCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteGoalCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoalCommand.MESSAGE_USAGE));
        }
    }

}
