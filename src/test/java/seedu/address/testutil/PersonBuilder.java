package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Meet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_BIRTHDAY = "01-01-1991";
    public static final String DEFAULT_LEVEL_OF_FRIENDSHIP = "1";
    public static final String DEFAULT_UNIT_NUMBER = "#12-21";
    public static final String DEFAULT_CCAS = "hockey";
    public static final String DEFAULT_DATE = "";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Birthday birthday;
    private LevelOfFriendship levelOfFriendship;
    private UnitNumber unitNumber;
    private Set<Cca> ccas;
    private Meet meetDate;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        levelOfFriendship = new LevelOfFriendship(DEFAULT_LEVEL_OF_FRIENDSHIP);
        unitNumber = new UnitNumber(DEFAULT_UNIT_NUMBER);
        ccas = SampleDataUtil.getCcaSet(DEFAULT_CCAS);
        meetDate = new Meet(DEFAULT_DATE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        birthday = personToCopy.getBirthday();
        levelOfFriendship = personToCopy.getLevelOfFriendship();
        unitNumber = personToCopy.getUnitNumber();
        meetDate = personToCopy.getMeetDate();
        ccas = new HashSet<>(personToCopy.getCcas());
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code ccas} into a {@code Set<Cca>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCcas(String ... ccas) {
        this.ccas = SampleDataUtil.getCcaSet(ccas);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }

    /**
     * Sets the {@code LevelOfFriendship} of the {@code Person} that we are building.
     */
    public PersonBuilder withLevelOfFriendship(String levelOfFriendship) {
        this.levelOfFriendship = new LevelOfFriendship(levelOfFriendship);
        return this;
    }

    /**
     * Sets the {@code UnitNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withUnitNumber(String unitNumber) {
        this.unitNumber = new UnitNumber(unitNumber);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withMeetDate(String meetDate) {
        this.meetDate = new Meet(meetDate);
        return this;
    }

    public Person build() {
        return new Person(name, phone, birthday, levelOfFriendship, unitNumber, ccas, meetDate, tags);
    }

}
