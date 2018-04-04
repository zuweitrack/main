package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.UniqueGoalList;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.goal.exceptions.GoalNotFoundException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueCcaList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueCcaList ccas;
    private final UniqueTagList tags;
    private final UniqueGoalList goals;
    private final UniqueReminderList reminders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        ccas = new UniqueCcaList();
        tags = new UniqueTagList();
        goals = new UniqueGoalList();
        reminders = new UniqueReminderList();
    }

    public AddressBook() {}

    /**
     * Creates a CollegeZone using the Persons, Ccas, Tags and Goals in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    //@@author deborahlow97
    public void setCcas(Set<Cca> ccas) {
        this.ccas.setCcas(ccas); }

    //@@author
    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    //@@author deborahlow97
    public void setGoals(List<Goal> goals) throws DuplicateGoalException {
        this.goals.setGoals(goals);
    }


    public void setReminders(List<Reminder> reminders) throws DuplicateReminderException {
        this.reminders.setReminders(reminders);
    }

    //@@author

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setCcas(new HashSet<>(newData.getCcaList()));
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterCcaList)
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("CollegeZone should not have duplicate persons");
        }

        List<Goal> syncedGoalList = newData.getGoalList().stream().collect(Collectors.toList());
        try {
            setGoals(syncedGoalList);
        } catch (DuplicateGoalException e) {
            throw new AssertionError("Goal Page should not have duplicate goals");
        }

        List<Reminder> syncedReminderList = newData.getReminderList().stream().collect(Collectors.toList());
        try {
            setReminders(syncedReminderList);
        } catch (DuplicateReminderException e) {
            throw new AssertionError("Reminder list should not have duplicate reminders");
        }
    }

    //// person-level operations

    /**
     * Adds a person to CollegeZone.
     * Also checks the new person's ccas and tags, and updates {@link #ccas #tags} with any new cca and tags found,
     * and updates the Cca and Tag objects in the person to point to those in {@link #ccas #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterCcaList(p);
        person = syncWithMasterTagList(person);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code CollegeZone}'s cca and tag list will be updated with the ccas and tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterCcaList(Person)
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);
        Person syncedEditedPerson = syncWithMasterCcaList(editedPerson);
        syncedEditedPerson = syncWithMasterTagList(syncedEditedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedCcas();
        removeUnusedTags();
    }

    //@@author deborahlow97
    /**
     * Removes all {@code Ccas}s that are not used by any {@code Person} in this {@code AddressBook}.
     */
    private void removeUnusedCcas() {
        Set<Cca> ccasInPersons = persons.asObservableList().stream()
                .map(Person::getCcas)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        ccas.setCcas(ccasInPersons);
    }

    //@@author
    /**
     * Removes all {@code Tag}s that are not used by any {@code Person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }

    //@@author deborahlow97
    /**
     *  Updates the master cca list to include ccas in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every cca in this person points to a Cca object in the master
     *  list.
     */
    private Person syncWithMasterCcaList(Person person) {
        final UniqueCcaList personCcas = new UniqueCcaList(person.getCcas());
        ccas.mergeFrom(personCcas);

        // Create map with values = cca object references in the master list
        // used for checking person cca references
        final Map<Cca, Cca> masterCcaObjects = new HashMap<>();
        ccas.forEach(cca -> masterCcaObjects.put(cca, cca));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Cca> correctCcaReferences = new HashSet<>();
        personCcas.forEach(cca -> correctCcaReferences.add(masterCcaObjects.get(cca)));
        return new Person(
                person.getName(), person.getPhone(), person.getBirthday(),
                person.getLevelOfFriendship(),  person.getUnitNumber(), correctCcaReferences, person.getMeetDate(),
                person.getTags());
    }

    //@@author
    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getBirthday(),
                person.getLevelOfFriendship(),  person.getUnitNumber(), person.getCcas(), person.getMeetDate(),
                correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// cca-level operations

    //@@author deborahlow97
    public void addCca(Cca cca) throws UniqueCcaList.DuplicateCcaException {
        ccas.add(cca);
    }

    //// tag-level operations

    //@@author
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }
    /**
     * Removes {@code tag} from all persons in this {@code AddressBook}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is obtained from the address book.");
        }
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(person.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Person newPerson =
                new Person(person.getName(), person.getPhone(), person.getBirthday(), person.getLevelOfFriendship(),
                        person.getUnitNumber(), person.getCcas(), person.getMeetDate(), newTags);
        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    //// goal-level operations

    //@@author deborahlow97
    /**
     * Adds a goal to CollegeZone.
     * @throws DuplicateGoalException if an equivalent goal already exists.
     */
    public void addGoal(Goal g) throws DuplicateGoalException {
        goals.add(g);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws GoalNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeGoal(Goal key) throws GoalNotFoundException {
        if (goals.remove(key)) {
            return true;
        } else {
            throw new GoalNotFoundException();
        }
    }

    /**
     * Replaces the given goal {@code target} in the list with {@code editedGoal}.
     *
     * @throws DuplicateGoalException if updating the goal's details causes the goal to be equivalent to
     *      another existing goal in the list.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void updateGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException {
        requireNonNull(editedGoal);
        goals.setGoal(target, editedGoal);
    }

    /**
     * Replaces the given goal {@code target} in the list with {@code editedGoal}.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void updateGoalWithoutParameters(Goal target, Goal editedGoal) throws GoalNotFoundException {
        requireNonNull(editedGoal);
        goals.setGoalWithoutParameters(target, editedGoal);
    }

    //// reminder-level operations

    //@@author fuadsahmawi
    /**
     * Adds a reminder to CollegeZone.
     * @throws DuplicateReminderException if an equivalent reminder already exists.
     */
    public void addReminder (Reminder r) throws DuplicateReminderException {
        reminders.add(r);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void updateReminder(Reminder target, Reminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReminder);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        reminders.setReminder(target, editedReminder);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + ccas.asObservableList().size()
                + "ccas, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Cca> getCcaList() {
        return ccas.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Goal> getGoalList() {
        return goals.asObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.ccas.equalsOrderInsensitive(((AddressBook) other).ccas)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, ccas, tags);
    }
}
