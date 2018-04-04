package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Meet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String levelOfFriendship;
    @XmlElement(required = true)
    private String unitNumber;
    @XmlElement(required = true)
    private String meetDate;

    @XmlElement
    private List<XmlAdaptedCca> ccas = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String birthday, String levelOfFriendship, String unitNumber,
                            String meetDate, List<XmlAdaptedCca> ccas, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.levelOfFriendship = levelOfFriendship;
        this.unitNumber = unitNumber;
        this.meetDate = meetDate;
        if (ccas != null) {
            this.ccas = new ArrayList<>(ccas);
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        birthday = source.getBirthday().value;
        levelOfFriendship = source.getLevelOfFriendship().value;
        unitNumber = source.getUnitNumber().value;
        meetDate = source.getMeetDate().value;
        ccas = new ArrayList<>();
        for (Cca cca : source.getCcas()) {
            ccas.add(new XmlAdaptedCca(cca));
        }
        tagged = new ArrayList<>();

        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Cca> personCcas = new ArrayList<>();
        for (XmlAdaptedCca cca : ccas) {
            personCcas.add(cca.toModelType());
        }
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.birthday == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Birthday.class.getSimpleName()));
        }
        if (!Birthday.isValidBirthday(this.birthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        final Birthday birthday = new Birthday(this.birthday);

        if (this.levelOfFriendship == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LevelOfFriendship
                    .class.getSimpleName()));
        }
        if (!LevelOfFriendship.isValidLevelOfFriendship(this.levelOfFriendship)) {
            throw new IllegalValueException(LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        }
        final LevelOfFriendship levelOfFriendship = new LevelOfFriendship(this.levelOfFriendship);

        /*if (!Meet.isValidDate(this.meetDate)) {
            throw new IllegalValueException(Meet.MESSAGE_DATE_CONSTRAINTS);
        }*/
        final Meet meetDate = new Meet(this.meetDate);

        if (this.unitNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitNumber.class.getSimpleName()));
        }
        if (!UnitNumber.isValidUnitNumber(this.unitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);
        }
        final UnitNumber unitNumber = new UnitNumber(this.unitNumber);
        final Set<Cca> ccas = new HashSet<>(personCcas);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, birthday, levelOfFriendship, unitNumber, ccas, meetDate, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(birthday, otherPerson.birthday)
                && Objects.equals(levelOfFriendship, otherPerson.levelOfFriendship)
                && Objects.equals(unitNumber, otherPerson.unitNumber)
                //&& Objects.equals(meetDate, otherPerson.meetDate)
                && ccas.equals(otherPerson.ccas)
                && tagged.equals(otherPerson.tagged);
    }
}
