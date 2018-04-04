package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Meet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CollegeZone} with sample data.
 */
public class SampleDataUtil {

    public static final Meet EMPTY_MEET_DATE = new Meet("15/04/2018");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("1-01-1997"),
                new LevelOfFriendship("5"), new UnitNumber("#06-40"), getCcaSet("Basketball"),
                EMPTY_MEET_DATE, getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Birthday("21-2-1990"),
                new LevelOfFriendship("9"), new UnitNumber("#07-18"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Birthday("5/9/1980"),
                new LevelOfFriendship("1"), new UnitNumber("#11-04"), getCcaSet("Swimming"),
                EMPTY_MEET_DATE, getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Birthday("20-2-1995"),
                new LevelOfFriendship("6"), new UnitNumber("#16-43"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Birthday("01-01-1999"),
                new LevelOfFriendship("7"), new UnitNumber("#16-41"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Birthday("2/04/1995"),
                new LevelOfFriendship("10"), new UnitNumber("#6-43"), getCcaSet("Computing club", "Anime Club"),
                EMPTY_MEET_DATE, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a cca set containing the list of strings given.
     */
    public static Set<Cca> getCcaSet(String... strings) {
        HashSet<Cca> ccas = new HashSet<>();
        for (String s : strings) {
            ccas.add(new Cca(s));
        }

        return ccas;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
