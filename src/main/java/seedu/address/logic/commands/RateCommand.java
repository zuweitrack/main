package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL_OF_FRIENDSHIP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author zuweitrack
/**
 * Rates existing person(s) in CollegeZone.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rates person(s) in College Zone "
            + "and changes the level of friendship "
            + "by the index number used in the latest listing.\n"
            + "Existing level of friendship will be overwritten by the input values.\n"
            + "Parameters: INDEX(s) (must be a positive integer) "
            + "[" + PREFIX_LEVEL_OF_FRIENDSHIP + "LEVELOFFRIENDSHIP] (between 1 and 10)\n"
            + "Example: " + COMMAND_WORD + " 1 3 "
            + PREFIX_LEVEL_OF_FRIENDSHIP + "5 ";

    private static final String MESSAGE_EDIT_PERSON_SUCCESS = "Rated person(s) successfully";
    private static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private static final String MESSAGE_PERSON_NOT_FOUND = "The selected person cannot be missing";
    private static final String MESSAGE_ONE_OR_MORE_INVALID_INDEX =
            "One or more index inputs may not be valid"
                    + " and only the person(s) of valid indexes are being rated!";

    private final List<Index> indexList;
    private final String levelOfFriendship;

    /**
     * @param indexList list of index(es) of the person in the filtered person list
     * @param levelOfFriendship new level of friendship to add to the person
     */
    public RateCommand(List<Index> indexList, String levelOfFriendship) {
        requireNonNull(indexList);
        requireNonNull(levelOfFriendship);

        this.indexList = indexList;
        this.levelOfFriendship = levelOfFriendship;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Person> latestList = model.getFilteredPersonList();
        for (Index index : indexList) {

            if (index.getZeroBased() >= latestList.size()) {
                throw new CommandException(MESSAGE_ONE_OR_MORE_INVALID_INDEX);
            }

            Person selectedPerson = latestList.get(index.getZeroBased());

            try {
                Person editedPerson = new Person(selectedPerson.getName(), selectedPerson.getPhone(),
                        selectedPerson.getBirthday(), new LevelOfFriendship(levelOfFriendship),
                        selectedPerson.getUnitNumber(),
                        selectedPerson.getCcas(), selectedPerson.getMeetDate(), selectedPerson.getTags());
                model.updatePerson(selectedPerson, editedPerson);

            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);

            }

        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);

    }

}
