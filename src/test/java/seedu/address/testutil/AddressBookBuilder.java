package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withCca("ballet)
 *     .withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        try {
            addressBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a new {@code Goal} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withGoal(Goal goal) {
        try {
            addressBook.addGoal(goal);
        } catch (DuplicateGoalException dge) {
            throw new IllegalArgumentException("goal is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code ccaName} into a {@code Cca} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withCca(String ccaName) {
        try {
            addressBook.addCca(new Cca(ccaName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("ccaName is expected to be valid.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            addressBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
