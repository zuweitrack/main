package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CCA_DESC_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.CCA_DESC_DANCE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CCA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_OF_FRIENDSHIP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_OF_FRIENDSHIP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_OF_FRIENDSHIP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_DANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_OF_FRIENDSHIP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_OF_FRIENDSHIP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB)
                .withUnitNumber(VALID_UNIT_NUMBER_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple level of friendships - last level of friendship accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + LEVEL_OF_FRIENDSHIP_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_BOB
                        + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple unit numbers - last unit number accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + UNIT_NUMBER_DESC_AMY + UNIT_NUMBER_DESC_BOB + LEVEL_OF_FRIENDSHIP_DESC_BOB
                + CCA_DESC_BADMINTON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple ccas - all accepted
        Person expectedPersonMultipleCcas = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB)
                .withUnitNumber(VALID_UNIT_NUMBER_BOB).withCcas(VALID_CCA_BADMINTON, VALID_CCA_DANCE)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON + CCA_DESC_DANCE
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleCcas));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB)
                .withUnitNumber(VALID_UNIT_NUMBER_BOB).withCcas(VALID_CCA_DANCE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }


    @Test
    public void parse_optionalFieldsMissing_success() {

        // zero ccas
        Person expectedPersonA = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY)
                .withUnitNumber(VALID_UNIT_NUMBER_AMY).withCcas().withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonA));

        // zero tags
        Person expectedPersonB = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY)
                .withUnitNumber(VALID_UNIT_NUMBER_AMY).withCcas(VALID_CCA_DANCE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + CCA_DESC_DANCE,
                new AddCommand(expectedPersonB));

        // zero ccas and tags
        Person expectedPersonC = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY)
                .withUnitNumber(VALID_UNIT_NUMBER_AMY).withCcas().withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY,
                new AddCommand(expectedPersonC));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB, expectedMessage);

        // missing birthday prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_BIRTHDAY_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB, expectedMessage);

        //missing level of friendship prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + VALID_LEVEL_OF_FRIENDSHIP_BOB + UNIT_NUMBER_DESC_BOB, expectedMessage);

        // missing unit number prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + VALID_UNIT_NUMBER_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_BIRTHDAY_BOB
                + VALID_LEVEL_OF_FRIENDSHIP_BOB + VALID_UNIT_NUMBER_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                + CCA_DESC_BADMINTON + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                + CCA_DESC_BADMINTON + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid birthday
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_BIRTHDAY_DESC
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                + CCA_DESC_BADMINTON + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        // invalid level of friendship
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + INVALID_LEVEL_OF_FRIENDSHIP_DESC + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                        + CCA_DESC_BADMINTON + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);


        // invalid unit number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + LEVEL_OF_FRIENDSHIP_DESC_BOB + INVALID_UNIT_NUMBER_DESC + CCA_DESC_DANCE
                        + CCA_DESC_BADMINTON + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);

        // invalid cca
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + INVALID_CCA_DESC
                + VALID_TAG_FRIEND + VALID_TAG_HUSBAND, Cca.MESSAGE_CCA_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_BADMINTON
                + CCA_DESC_DANCE + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_BIRTHDAY_DESC
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE + CCA_DESC_BADMINTON
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
