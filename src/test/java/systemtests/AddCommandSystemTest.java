package systemtests;

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
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_NUMBER_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.JAKE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + BIRTHDAY_DESC_AMY + "   " + LEVEL_OF_FRIENDSHIP_DESC_AMY + "   " + UNIT_NUMBER_DESC_AMY
                + "   " + CCA_DESC_DANCE + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + CCA_DESC_DANCE + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + CCA_DESC_DANCE
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_BOB)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_BOB
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + CCA_DESC_DANCE
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except level of
           friendship -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB).withUnitNumber(VALID_UNIT_NUMBER_AMY)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_AMY + CCA_DESC_DANCE
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except unit
           number -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY).withUnitNumber(VALID_UNIT_NUMBER_BOB)
                .withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with ccas, command with parameters in random order -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB).withUnitNumber(VALID_UNIT_NUMBER_BOB)
                .withCcas(VALID_CCA_BADMINTON).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB + NAME_DESC_BOB
                + CCA_DESC_BADMINTON + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        deleteAllPersons();
        //BUGGED
        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB + NAME_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + LEVEL_OF_FRIENDSHIP_DESC_BOB + UNIT_NUMBER_DESC_BOB + CCA_DESC_DANCE
                + CCA_DESC_BADMINTON;
        assertCommandSuccess(command, toAdd);

        deleteAllPersons();

        /* Case: add a person, missing ccas -> added */
        assertCommandSuccess(IDA);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: add a person, missing tags and ccas -> added */
        assertCommandSuccess(JAKE);
        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);

        //TODO
        /* ------------------------ Perform add operation while a person card is selected --------------------------- */
        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        //selectPerson(Index.fromOneBased(1));
        //assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different ccas -> rejected */
        // "friends" is an existing cca used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new cca that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(IDA) + " " + PREFIX_CCA.getPrefix() + "hockey";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_AMY
                + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + BIRTHDAY_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_AMY
                + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + LEVEL_OF_FRIENDSHIP_DESC_AMY
                + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing level of friendship -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing unit number -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_BIRTHDAY_DESC
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        /* Case: invalid level of friendship -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + INVALID_LEVEL_OF_FRIENDSHIP_DESC + UNIT_NUMBER_DESC_AMY;
        assertCommandFailure(command, LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);

        /* Case: invalid unit number -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + INVALID_UNIT_NUMBER_DESC;
        assertCommandFailure(command, UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);

        /* Case: invalid cca -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + INVALID_CCA_DESC;
        assertCommandFailure(command, Cca.MESSAGE_CCA_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + LEVEL_OF_FRIENDSHIP_DESC_AMY + UNIT_NUMBER_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
