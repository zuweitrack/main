package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteMeetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetCommand object
 */
public class DeleteMeetCommandParser implements Parser<DeleteMeetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetCommand
     * and returns an DeleteMeetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMeetCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetCommand.MESSAGE_USAGE));
        }
    }

}
