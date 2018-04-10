package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL_OF_FRIENDSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Cca;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY,
                        PREFIX_LEVEL_OF_FRIENDSHIP,  PREFIX_UNIT_NUMBER, PREFIX_CCA, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY))
                    .ifPresent(editPersonDescriptor::setBirthday);
            ParserUtil.parseLevelOfFriendship(argMultimap.getValue(PREFIX_LEVEL_OF_FRIENDSHIP))
                    .ifPresent(editPersonDescriptor::setLevelOfFriendship);
            ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNIT_NUMBER))
                    .ifPresent(editPersonDescriptor::setUnitNumber);
            parseCcasForEdit(argMultimap.getAllValues(PREFIX_CCA)).ifPresent(editPersonDescriptor::setCcas);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    //@@author deborahlow97
    /**
     * Parses {@code Collection<String> ccas} into a {@code Set<Cca>} if {@code ccas} is non-empty.
     * If {@code ccas} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Cca>} containing zero ccas.
     */
    private Optional<Set<Cca>> parseCcasForEdit(Collection<String> ccas) throws IllegalValueException {
        assert ccas != null;

        if (ccas.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> ccaSet = ccas.size() == 1 && ccas.contains("") ? Collections.emptySet() : ccas;
        return Optional.of(ParserUtil.parseCcas(ccaSet));
    }

    //@@author
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
