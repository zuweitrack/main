package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Birthday birthday;
    private final LevelOfFriendship levelOfFriendship;
    private final UnitNumber unitNumber;
    private final Meet meetDate;
    private final UniqueCcaList ccas;
    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Birthday birthday, LevelOfFriendship levelOfFriendship,
                  UnitNumber unitNumber, Set<Cca> ccas, Meet meetDate, Set<Tag> tags) {
        requireAllNonNull(name, phone, birthday, levelOfFriendship, unitNumber, ccas, tags);
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.levelOfFriendship = levelOfFriendship;
        this.unitNumber = unitNumber;
        this.meetDate = meetDate;
        // protect internal tags and ccas from changes in the arg list
        this.ccas = new UniqueCcaList(ccas);
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public LevelOfFriendship getLevelOfFriendship() {
        return levelOfFriendship;
    }

    public Meet getMeetDate() {
        return meetDate;
    }
    public UnitNumber getUnitNumber() {
        return unitNumber;
    }
    /**
     * Returns an immutable cca set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Cca> getCcas() {
        return Collections.unmodifiableSet(ccas.toSet());
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getBirthday().equals(this.getBirthday())
                && otherPerson.getLevelOfFriendship().equals(this.getLevelOfFriendship())
                && otherPerson.getUnitNumber().equals(this.getUnitNumber());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, birthday, levelOfFriendship, unitNumber, ccas, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Birthday: ")
                .append(getBirthday())
                .append(" Level Of Friendship: ")
                .append(getLevelOfFriendship())
                .append(" Unit Number: ")
                .append(getUnitNumber())
                .append(" Ccas: ");
        getCcas().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
