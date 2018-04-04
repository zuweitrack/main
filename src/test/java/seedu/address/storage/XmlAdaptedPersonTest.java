package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_UNIT_NUMBER = " ";
    private static final String INVALID_BIRTHDAY = "example";
    private static final String INVALID_LEVEL_OF_FRIENDSHIP = "10a";
    private static final String INVALID_CCA = "222!@";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_BIRTHDAY = BENSON.getBirthday().toString();
    private static final String VALID_LEVEL_OF_FRIENDSHIP = BENSON.getLevelOfFriendship().toString();
    private static final String VALID_UNIT_NUMBER = BENSON.getUnitNumber().toString();
    private static final String VALID_MEETDATE = BENSON.getMeetDate().toString();
    private static final List<XmlAdaptedCca> VALID_CCAS = BENSON.getCcas().stream()
            .map(XmlAdaptedCca::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE, VALID_BIRTHDAY,
                VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_BIRTHDAY,
                VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBirthday_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBirthday_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Birthday.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLevelOfFriendship_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                        INVALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLevelOfFriendship_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                 null, VALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LevelOfFriendship.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidUnitNumber_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, INVALID_UNIT_NUMBER, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUnitNumber_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                VALID_LEVEL_OF_FRIENDSHIP, null, VALID_MEETDATE, VALID_CCAS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UnitNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCcas_throwsIllegalValueException() {
        List<XmlAdaptedCca> invalidCcas = new ArrayList<>(VALID_CCAS);
        invalidCcas.add(new XmlAdaptedCca(INVALID_CCA));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, "", invalidCcas, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_BIRTHDAY,
                        VALID_LEVEL_OF_FRIENDSHIP, VALID_UNIT_NUMBER, "", VALID_CCAS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
