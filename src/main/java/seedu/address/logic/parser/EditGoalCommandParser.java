package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.EditGoalCommand.EditGoalDescriptor;
import seedu.address.logic.commands.EditGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author deborahlow97
/**
 * Parses input arguments and creates a new EditGoalCommand object
 */
public class EditGoalCommandParser implements Parser<EditGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditGoalCommand
     * and returns an EditGoalCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGoalCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GOAL_TEXT, PREFIX_IMPORTANCE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGoalCommand.MESSAGE_USAGE));
        }

        EditGoalDescriptor editGoalDescriptor = new EditGoalDescriptor();
        try {
            ParserUtil.parseGoalText(argMultimap.getValue(PREFIX_GOAL_TEXT)).ifPresent(editGoalDescriptor::setGoalText);
            ParserUtil.parseImportance(argMultimap.getValue(PREFIX_IMPORTANCE))
                    .ifPresent(editGoalDescriptor::setImportance);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editGoalDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditGoalCommand.MESSAGE_NOT_EDITED);
        }

        return new EditGoalCommand(index, editGoalDescriptor);
    }
}
