package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CompleteGoalCommand;
import seedu.address.logic.commands.CompleteGoalCommand.CompleteGoalDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new CompleteGoalCommand object
 */
public class CompleteGoalCommandParser implements Parser<CompleteGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteGoalCommand
     * and returns an CompleteGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteGoalCommand parse(String args) throws ParseException {

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteGoalCommand.MESSAGE_USAGE));
        }

        CompleteGoalDescriptor completeGoalDescriptor = new CompleteGoalDescriptor();

        Optional<String> empty = Optional.empty();
        Completion completion = new Completion(true);
        EndDateTime endDateTime = new EndDateTime("today");
        completeGoalDescriptor.setCompletion(completion);
        completeGoalDescriptor.setEndDateTime(endDateTime);


        return new CompleteGoalCommand(index, completeGoalDescriptor);
    }
}
