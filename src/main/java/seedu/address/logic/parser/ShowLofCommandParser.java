package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ShowLofCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LofContainsValuePredicate;

//@@author zuweitrack
/**
 * Parses input arguments and creates a new ShowLofCommand object
 */
public class ShowLofCommandParser implements Parser<ShowLofCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowLofCommand
     * and returns an ShowLofCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowLofCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLofCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = (trimmedArgs.split("\\s+"));

        return new ShowLofCommand(new LofContainsValuePredicate(Arrays.asList(nameKeywords)));
    }

}
