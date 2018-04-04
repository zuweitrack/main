package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

//@@author fuadsahmawi
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        FindPersonDescriptor findPersonDescriptor = new FindPersonDescriptor();

        argMultimap.getValue(PREFIX_NAME).ifPresent(findPersonDescriptor::setNameKeywords);
        argMultimap.getValue(PREFIX_TAG).ifPresent(findPersonDescriptor::setTagKeywords);


        if (!findPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_EDITED);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return new FindCommand(
                    new NameContainsKeywordsPredicate(Arrays.asList(findPersonDescriptor.getNameKeywords())));
        } else {
            return new FindCommand(
                    new TagContainsKeywordsPredicate(Arrays.asList(findPersonDescriptor.getTagKeyWords())));
        }
    }
}
