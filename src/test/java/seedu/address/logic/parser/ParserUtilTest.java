package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_BIRTHDAY = "24 maya 1997";
    private static final String INVALID_LEVEL_OF_FRIENDSHIP = "A";
    private static final String INVALID_UNIT_NUMBER = " ";
    private static final String INVALID_CCA = "!345hockey";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_BIRTHDAY = "12-3-1992";
    private static final String VALID_LEVEL_OF_FRIENDSHIP = "1";
    private static final String VALID_UNIT_NUMBER = "#04-33";
    private static final String VALID_CCA_1 = "scouts";
    private static final String VALID_CCA_2 = "aerobics";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((Optional<String>) null));
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(INVALID_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(Optional.of(INVALID_NAME)));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((Optional<String>) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(Optional.of(INVALID_PHONE)));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(VALID_PHONE)));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(phoneWithWhitespace)));
    }

    @Test
    public void parseBirthday_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((Optional<String>) null));
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(INVALID_BIRTHDAY));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY)));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValueWithoutWhitespace_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        assertEquals(expectedBirthday, ParserUtil.parseBirthday(VALID_BIRTHDAY));
        assertEquals(Optional.of(expectedBirthday), ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY)));
    }

    @Test
    public void parseBirthday_validValueWithWhitespace_returnsTrimmedBirthday() throws Exception {
        String birthdayWithWhitespace = WHITESPACE + VALID_BIRTHDAY + WHITESPACE;
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        assertEquals(expectedBirthday, ParserUtil.parseBirthday(birthdayWithWhitespace));
        assertEquals(Optional.of(expectedBirthday), ParserUtil.parseBirthday(Optional.of(birthdayWithWhitespace)));
    }

    @Test
    public void parseLevelOfFriendship_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLevelOfFriendship((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseLevelOfFriendship((Optional<String>) null));
    }

    @Test
    public void parseLevelOfFriendship_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseLevelOfFriendship(INVALID_LEVEL_OF_FRIENDSHIP));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseLevelOfFriendship(Optional.of(INVALID_LEVEL_OF_FRIENDSHIP)));
    }

    @Test
    public void parseLevelOfFriendship_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseLevelOfFriendship(Optional.empty()).isPresent());
    }

    @Test
    public void parseLevelOfFriendship_validValueWithoutWhitespace_returnsLevelOfFriendship() throws Exception {
        LevelOfFriendship expectedLevelOfFriendship = new LevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP);
        assertEquals(expectedLevelOfFriendship, ParserUtil.parseLevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP));
        assertEquals(Optional.of(expectedLevelOfFriendship), ParserUtil
                .parseLevelOfFriendship(Optional.of(VALID_LEVEL_OF_FRIENDSHIP)));
    }

    @Test
    public void parseLevelOfFriendship_validValueWithWhitespace_returnsTrimmedLevelOfFriendship() throws Exception {
        String levelOfFriendshipWithWhitespace = WHITESPACE + VALID_LEVEL_OF_FRIENDSHIP + WHITESPACE;
        LevelOfFriendship expectedLevelOfFriendship = new LevelOfFriendship(VALID_LEVEL_OF_FRIENDSHIP);
        assertEquals(expectedLevelOfFriendship, ParserUtil.parseLevelOfFriendship(levelOfFriendshipWithWhitespace));
        assertEquals(Optional.of(expectedLevelOfFriendship), ParserUtil
                .parseLevelOfFriendship(Optional.of(levelOfFriendshipWithWhitespace)));
    }

    @Test
    public void parseUnitNumber_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUnitNumber((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUnitNumber((Optional<String>) null));
    }

    @Test
    public void parseUnitNumber_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUnitNumber(INVALID_UNIT_NUMBER));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil
                .parseUnitNumber(Optional.of(INVALID_UNIT_NUMBER)));
    }

    @Test
    public void parseUnitNumber_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseUnitNumber(Optional.empty()).isPresent());
    }

    @Test
    public void parseUnitNumber_validValueWithoutWhitespace_returnsUnitNumber() throws Exception {
        UnitNumber expectedUnitNumber = new UnitNumber(VALID_UNIT_NUMBER);
        assertEquals(expectedUnitNumber, ParserUtil.parseUnitNumber(VALID_UNIT_NUMBER));
        assertEquals(Optional.of(expectedUnitNumber), ParserUtil.parseUnitNumber(Optional.of(VALID_UNIT_NUMBER)));
    }

    @Test
    public void parseUnitNumber_validValueWithWhitespace_returnsTrimmedUnitNumber() throws Exception {
        String unitNumberWithWhitespace = WHITESPACE + VALID_UNIT_NUMBER + WHITESPACE;
        UnitNumber expectedUnitNumber = new UnitNumber(VALID_UNIT_NUMBER);
        assertEquals(expectedUnitNumber, ParserUtil.parseUnitNumber(unitNumberWithWhitespace));
        assertEquals(Optional.of(expectedUnitNumber), ParserUtil.parseUnitNumber(Optional
                .of(unitNumberWithWhitespace)));
    }

    @Test
    public void parseCca_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCca(null);
    }

    @Test
    public void parseCca_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseCca(INVALID_CCA);
    }

    @Test
    public void parseCca_validValueWithoutWhitespace_returnsCca() throws Exception {
        Cca expectedCca = new Cca(VALID_CCA_1);
        assertEquals(expectedCca, ParserUtil.parseCca(VALID_CCA_1));
    }

    @Test
    public void parseCca_validValueWithWhitespace_returnsTrimmedCca() throws Exception {
        String ccaWithWhitespace = WHITESPACE + VALID_CCA_1 + WHITESPACE;
        Cca expectedCca = new Cca(VALID_CCA_1);
        assertEquals(expectedCca, ParserUtil.parseCca(ccaWithWhitespace));
    }

    @Test
    public void parseCcas_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCcas(null);
    }

    @Test
    public void parseCcas_collectionWithInvalidCcas_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseCcas(Arrays.asList(VALID_CCA_1, INVALID_CCA));
    }

    @Test
    public void parseCcas_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCcas(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCcas_collectionWithValidCcas_returnsCcaSet() throws Exception {
        Set<Cca> actualCcaSet = ParserUtil.parseCcas(Arrays.asList(VALID_CCA_1, VALID_CCA_2));
        Set<Cca> expectedCcaSet = new HashSet<Cca>(Arrays.asList(new Cca(VALID_CCA_1), new Cca(VALID_CCA_2)));

        assertEquals(expectedCcaSet, actualCcaSet);
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
