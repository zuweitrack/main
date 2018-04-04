package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.goal.exceptions.GoalNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.tag.Tag;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Goal> PREDICATE_SHOW_ALL_GOALS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Removes the given {@code tag} from all {@code Person}s. */
    void deleteTag(Tag tag);

    //@@author deborahlow97
    /** Adds the given goal */
    void addGoal(Goal goal) throws DuplicateGoalException;

    /** Returns an unmodifiable view of the filtered goal list */
    ObservableList<Goal> getFilteredGoalList();

    /**
     * Replaces the given goal {@code target} with {@code editedGoal}.
     *
     * @throws DuplicateGoalException if updating the goal's details causes the goal to be equivalent to
     *      another existing goal in the list.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    void updateGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException;

    /**
     * Updates the filter of the filtered goal list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGoalList(Predicate<Goal> predicate);

    /** Deletes the given goal. */
    void deleteGoal(Goal target) throws GoalNotFoundException;

    /**
     * Replaces the given goal {@code target} with {@code updateGoal}.
     *
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    void updateGoalWithoutParameters(Goal target, Goal editedGoal) throws GoalNotFoundException;

    //@@author fuadsahmawi
    /** Adds the given reminder. */
    void addReminder(Reminder reminder) throws DuplicateReminderException;

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);
}
