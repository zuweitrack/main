package seedu.address.testutil;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;




/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("85355255").withBirthday("01-01-1991").withLevelOfFriendship("1")
            .withUnitNumber("#12-21").withCcas("hockey").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432").withBirthday("19-01-1998").withLevelOfFriendship("2")
            .withUnitNumber("#4-44").withCcas("Skating", "swimming").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withBirthday("12-02-1994").withLevelOfFriendship("7").withUnitNumber("#2-69").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withBirthday("12/03/1994").withLevelOfFriendship("4").withUnitNumber("#03-033").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withBirthday("09-09-1999").withLevelOfFriendship("10").withUnitNumber("#9-434")
            .withCcas("modern dance").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withBirthday("10/10/1990").withLevelOfFriendship("3").withUnitNumber("#10-10").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withBirthday("11/11/2000").withLevelOfFriendship("8").withUnitNumber("#2-65")
            .withMeetDate("14/04/2018").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withBirthday("15/05/1995").withLevelOfFriendship("1").withUnitNumber("#6-66")
            .withCcas("floorball").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withBirthday("14/04/1994").withLevelOfFriendship("3").withUnitNumber("#4-44")
            .withTags("colleagues").build();
    public static final Person JAKE = new PersonBuilder().withName("Jake Black").withPhone("8482131")
            .withBirthday("14/04/1995").withLevelOfFriendship("3").withUnitNumber("#4-45")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_AMY)
            .withUnitNumber(VALID_UNIT_NUMBER_AMY).withCcas(VALID_CCA_DANCE)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP_BOB)
            .withUnitNumber(VALID_UNIT_NUMBER_BOB)
            .withCcas(VALID_CCA_DANCE, VALID_CCA_BADMINTON)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
