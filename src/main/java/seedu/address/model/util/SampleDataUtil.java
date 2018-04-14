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

//@@author deborahlow97
/**
 * Contains utility methods for populating {@code CollegeZone} with sample data.
 */
public class SampleDataUtil {

    public static final Meet EMPTY_MEET_DATE = new Meet("15/04/2018");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("01/01/1997"),
                new LevelOfFriendship("5"), new UnitNumber("#06-40"), getCcaSet("Basketball"),
                EMPTY_MEET_DATE, getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Birthday("21/02/1990"),
                new LevelOfFriendship("9"), new UnitNumber("#07-18"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Birthday("05/09/1980"),
                new LevelOfFriendship("1"), new UnitNumber("#11-04"), getCcaSet("Swimming"),
                EMPTY_MEET_DATE, getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Birthday("20/02/1995"),
                new LevelOfFriendship("6"), new UnitNumber("#16-43"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Birthday("01/01/1999"),
                new LevelOfFriendship("7"), new UnitNumber("#16-41"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Birthday("02/04/1995"),
                new LevelOfFriendship("10"), new UnitNumber("#6-43"), getCcaSet("Computing club", "Anime Club"),
                EMPTY_MEET_DATE, getTagSet("colleagues")),
            new Person(new Name("James Mee"), new Phone("98887555"), new Birthday("22/08/1992"),
                new LevelOfFriendship("1"), new UnitNumber("#06-40"), getCcaSet("Tennis"),
                EMPTY_MEET_DATE, getTagSet("RA")),
            new Person(new Name("Jane Ray"), new Phone("93336444"), new Birthday("25/09/1991"),
                new LevelOfFriendship("1"), new UnitNumber("#07-40"), getCcaSet("Chess Club"),
                EMPTY_MEET_DATE, getTagSet("RA")),
            new Person(new Name("Deborah Low"), new Phone("91162930"), new Birthday("24/05/1997"),
                    new LevelOfFriendship("9"), new UnitNumber("#10-24"), getCcaSet("Aerobics Club"),
                    EMPTY_MEET_DATE, getTagSet("colleagues")),
            new Person(new Name("Royce Lew"), new Phone("93265932"), new Birthday("10/04/1996"),
                    new LevelOfFriendship("5"), new UnitNumber("#02-021"), getCcaSet(),
                    EMPTY_MEET_DATE, getTagSet("boyfriend")),
            new Person(new Name("Kaden Yeo"), new Phone("82350332"), new Birthday("28/03/2001"),
                    new LevelOfFriendship("6"), new UnitNumber("#6-20"), getCcaSet("shooting"),
                    EMPTY_MEET_DATE, getTagSet("friends")),
            new Person(new Name("Matthew Chiang"), new Phone("92624417"), new Birthday("02/04/1995"),
                    new LevelOfFriendship("4"), new UnitNumber("#20-43"), getCcaSet("Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("classmate")),
            new Person(new Name("Loh Sin Yuen"), new Phone("92624417"), new Birthday("02/05/1995"),
                    new LevelOfFriendship("10"), new UnitNumber("#03-63"), getCcaSet("dance"),
                    EMPTY_MEET_DATE, getTagSet("schoolmate")),
            new Person(new Name("Florence Chiang"), new Phone("92624417"), new Birthday("02/06/1995"),
                    new LevelOfFriendship("10"), new UnitNumber("#6-97"), getCcaSet("volleyball"),
                    EMPTY_MEET_DATE, getTagSet("bff")),
            new Person(new Name("Daniel Low"), new Phone("92624417"), new Birthday("12/04/1995"),
                    new LevelOfFriendship("1"), new UnitNumber("#7-473"), getCcaSet("Muay Thai"),
                    EMPTY_MEET_DATE, getTagSet("cousin")),
            new Person(new Name("Rachel Lee Yan Ling"), new Phone("92624417"), new Birthday("23/04/1995"),
                    new LevelOfFriendship("3"), new UnitNumber("#6-69"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("cousin")),
            new Person(new Name("Sarah tan"), new Phone("92624417"), new Birthday("27/04/1999"),
                    new LevelOfFriendship("2"), new UnitNumber("#8-43"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet()),
            new Person(new Name("Amanda Soh"), new Phone("92624417"), new Birthday("02/12/1995"),
                    new LevelOfFriendship("1"), new UnitNumber("#24-579"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("exgirlfriend")),
            new Person(new Name("Marlene Koh"), new Phone("92624417"), new Birthday("02/07/1997"),
                    new LevelOfFriendship("10"), new UnitNumber("#02-222"), getCcaSet("Pool"),
                    EMPTY_MEET_DATE, getTagSet("closefriend")),
            new Person(new Name("Johnny Depp"), new Phone("92624417"), new Birthday("02/12/1994"),
                    new LevelOfFriendship("2"), new UnitNumber("#01-346"), getCcaSet("Pool"),
                    EMPTY_MEET_DATE, getTagSet("malafriend")),
            new Person(new Name("Aditya"), new Phone("92624417"), new Birthday("02/04/1998"),
                    new LevelOfFriendship("3"), new UnitNumber("#6-43"), getCcaSet(),
                    EMPTY_MEET_DATE, getTagSet("malafriend")),
            new Person(new Name("Fuad"), new Phone("92624417"), new Birthday("20/04/1995"),
                    new LevelOfFriendship("9"), new UnitNumber("#6-43"), getCcaSet("Floorball"),
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
