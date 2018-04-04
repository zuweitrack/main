package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.goal.Goal;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    //@@author deborahlow97
    /**
     * Returns an unmodifiable view of the ccas list.
     * This list will not contain any duplicate ccas.
     */
    ObservableList<Cca> getCcaList();

    //@@author
    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    //@@author deborahlow97
    /**
     * Returns an unmodifiable view of the goals list.
     * This list will not contain any duplicate goals.
     */
    ObservableList<Goal> getGoalList();
    //@@author fuadsahmawi
    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<Reminder> getReminderList();

}
