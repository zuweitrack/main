package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new AddGoalCommand object
 */
public class AddGoalCommandParser implements Parser<AddGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGoalCommand
     * and returns an AddGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IMPORTANCE, PREFIX_GOAL_TEXT);

        if (!arePrefixesPresent(argMultimap, PREFIX_IMPORTANCE, PREFIX_GOAL_TEXT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoalCommand.MESSAGE_USAGE));
        }

        try {
            Importance importance = ParserUtil.parseImportance(argMultimap.getValue(PREFIX_IMPORTANCE)).get();
            GoalText goalText = ParserUtil.parseGoalText(argMultimap.getValue(PREFIX_GOAL_TEXT)).get();
            StartDateTime startDateTime = new StartDateTime(LocalDateTime.now());
            EndDateTime endDateTime = new EndDateTime("");
            Completion completion = new Completion(false);
            Goal goal = new Goal(importance, goalText, startDateTime, endDateTime, completion);
            return new AddGoalCommand(goal);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
