package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.DateTimeParser.properDateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.OngoingGoalCommand;
import seedu.address.logic.commands.OngoingGoalCommand.OngoingGoalDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new OngoingGoalCommand object
 */
public class OngoingGoalCommandParser implements Parser<OngoingGoalCommand> {

    public static final boolean ONGOING_BOOLEAN_VALUE = false;
    /**
     * Parses the given {@code String} of arguments in the context of the OngoingGoalCommand
     * and returns an OngoingGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OngoingGoalCommand parse(String args) throws ParseException {

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OngoingGoalCommand.MESSAGE_USAGE));
        }

        OngoingGoalDescriptor ongoingGoalDescriptor = new OngoingGoalDescriptor();

        Optional<String> empty = Optional.empty();
        Completion completion = new Completion(ONGOING_BOOLEAN_VALUE);
        EndDateTime endDateTime = new EndDateTime("");
        ongoingGoalDescriptor.setCompletion(completion);
        ongoingGoalDescriptor.setEndDateTime(endDateTime);


        return new OngoingGoalCommand(index, ongoingGoalDescriptor);
    }
}
