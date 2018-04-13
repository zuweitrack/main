package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
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
            + "[" + PREFIX_LEVEL_OF_FRIENDSHIP + "LEVELOFFRIENDSHIP]...\n"
            + "Example: " + COMMAND_WORD + " 1 3 "
            + PREFIX_LEVEL_OF_FRIENDSHIP + "5 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Rated successfully";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

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
            Person selectedPerson = latestList.get(index.getZeroBased());

            try {
                Person editedPerson = new Person(selectedPerson.getName(), selectedPerson.getPhone(),
                        selectedPerson.getBirthday(), new LevelOfFriendship(levelOfFriendship),
                        selectedPerson.getUnitNumber(),
                        selectedPerson.getCcas(), selectedPerson.getMeetDate(), selectedPerson.getTags());
                model.updatePerson(selectedPerson, editedPerson);

            } catch (PersonNotFoundException pnfe) {
                throw new CommandException("The selected person cannot be missing");
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);

            }

        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);



        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);

    }

}
