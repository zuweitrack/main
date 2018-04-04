package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Cca;
import seedu.address.model.person.LevelOfFriendship;
import seedu.address.model.person.Meet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.reminder.DateTime;
import seedu.address.model.reminder.EndDateTime;
import seedu.address.model.reminder.ReminderText;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    //@@author deborahlow97
    /**
     * Parses a {@code String birthday} into an {@code birthday}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Birthday parseBirthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!Birthday.isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        return new Birthday(trimmedBirthday);
    }

    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(parseBirthday(birthday.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String levelOfFriendship} into a {@code LevelOfFriendship}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code levelOfFriendship} is invalid.
     */
    public static LevelOfFriendship parseLevelOfFriendship(String levelOfFriendship) throws IllegalValueException {
        requireNonNull(levelOfFriendship);
        String trimmedLevelOfFriendship = levelOfFriendship.trim();
        if (!LevelOfFriendship.isValidLevelOfFriendship(trimmedLevelOfFriendship)) {
            throw new IllegalValueException(LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        }
        return new LevelOfFriendship(trimmedLevelOfFriendship);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LevelOfFriendship> parseLevelOfFriendship(Optional<String> levelOfFriendship)
            throws IllegalValueException {
        requireNonNull(levelOfFriendship);
        return levelOfFriendship.isPresent() ? Optional.of(parseLevelOfFriendship(levelOfFriendship.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String unitNumber} into an {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unitNumber} is invalid.
     */
    public static UnitNumber parseUnitNumber(String unitNumber) throws IllegalValueException {
        requireNonNull(unitNumber);
        String trimmedUnitNumber = unitNumber.trim();
        if (!UnitNumber.isValidUnitNumber(trimmedUnitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);
        }
        return new UnitNumber(trimmedUnitNumber);
    }

    /**
     * Parses a {@code Optional<String> unitNumber} into an {@code Optional<UnitNumber>} if {@code unitNumber}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<UnitNumber> parseUnitNumber(Optional<String> unitNumber) throws IllegalValueException {
        requireNonNull(unitNumber);
        return unitNumber.isPresent() ? Optional.of(parseUnitNumber(unitNumber.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String unitNumber} into an {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unitNumber} is invalid.
     */
    public static Meet parseMeetDate(String meetDate) throws IllegalValueException {
        requireNonNull(meetDate);
        String trimmedMeetDate = meetDate.trim();
        if (!Meet.isValidDate(trimmedMeetDate)) {
            throw new IllegalValueException(Meet.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Meet(trimmedMeetDate);
    }

    /**
     * Parses a {@code Optional<String> meetDate} into an {@code Optional<meetDate>} if {@code meetDate}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Meet> parseMeetDate(Optional<String> meetDate) throws IllegalValueException {
        requireNonNull(meetDate);
        return meetDate.isPresent() ? Optional.of(parseMeetDate(meetDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String cca} into a {@code Cca}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code cca} is invalid.
     */
    public static Cca parseCca(String cca) throws IllegalValueException {
        requireNonNull(cca);
        String trimmedCca = cca.trim();
        if (!Cca.isValidCcaName(trimmedCca)) {
            throw new IllegalValueException(Cca.MESSAGE_CCA_CONSTRAINTS);
        }
        return new Cca(trimmedCca);
    }

    /**
     * Parses {@code Collection<String> ccas} into a {@code Set<Cca>}.
     */
    public static Set<Cca> parseCcas(Collection<String> ccas) throws IllegalValueException {
        requireNonNull(ccas);
        final Set<Cca> ccaSet = new HashSet<>();
        for (String ccaName : ccas) {
            ccaSet.add(parseCca(ccaName));
        }
        return ccaSet;
    }

    //@@author
    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author deborahlow97
    /**
     * Parses a {@code String importance} into an {@code Importance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code importance} is invalid.
     */
    public static Importance parseImportance(String importance) throws IllegalValueException {
        requireNonNull(importance);
        String trimmedImportance = importance.trim();
        if (!Importance.isValidImportance(trimmedImportance)) {
            throw new IllegalValueException(Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);
        }
        return new Importance(trimmedImportance);
    }

    /**
     * Parses a {@code Optional<String> importance} into an {@code Optional<Importance>} if {@code importance}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Importance> parseImportance(Optional<String> importance) throws IllegalValueException {
        requireNonNull(importance);
        return importance.isPresent() ? Optional.of(parseImportance(importance.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String goalText} into an {@code GoalText}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code goalText} is invalid.
     */
    public static GoalText parseGoalText(String goalText) throws IllegalValueException {
        requireNonNull(goalText);
        String trimmedGoalText = goalText.trim();
        if (!GoalText.isValidGoalText(trimmedGoalText)) {
            throw new IllegalValueException(GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);
        }
        return new GoalText(trimmedGoalText);
    }

    /**
     * Parses a {@code Optional<String> goalText} into an {@code Optional<GoalText>} if {@code goalText}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GoalText> parseGoalText(Optional<String> goalText) throws IllegalValueException {
        requireNonNull(goalText);
        return goalText.isPresent() ? Optional.of(parseGoalText(goalText.get())) : Optional.empty();
    }

    //@@author fuadsahmawi

    /**
     * Parses a {@code String reminderText} into an {@code ReminderText}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code reminderText} is invalid.
     */
    public static ReminderText parseReminderText(String reminderText) throws IllegalValueException {
        requireNonNull(reminderText);
        String trimmedReminderText = reminderText.trim();
        if (!ReminderText.isValidReminderText(trimmedReminderText)) {
            throw new IllegalValueException(ReminderText.MESSAGE_REMINDER_TEXT_CONSTRAINTS);
        }
        return new ReminderText(trimmedReminderText);
    }

    /**
     * Parses a {@code Optional<String> reminderText} into an {@code Optional<ReminderText>} if {@code reminderText}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ReminderText> parseReminderText(Optional<String> reminderText) throws IllegalValueException {
        requireNonNull(reminderText);
        return reminderText.isPresent() ? Optional.of(parseReminderText(reminderText.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String reminderText} into an {@code ReminderText}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code reminderText} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code Optional<String> reminderText} into an {@code Optional<ReminderText>} if {@code reminderText}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        return dateTime.isPresent() ? Optional.of(parseDateTime(dateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String reminderText} into an {@code ReminderText}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code reminderText} is invalid.
     */
    public static EndDateTime parseEndDateTime(String endDateTime) throws IllegalValueException {
        requireNonNull(endDateTime);
        String trimmedEndDateTime = endDateTime.trim();
        if (!DateTime.isValidDateTime(trimmedEndDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        return new EndDateTime(trimmedEndDateTime);
    }

    /**
     * Parses a {@code Optional<String> reminderText} into an {@code Optional<ReminderText>} if {@code reminderText}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndDateTime> parseEndDateTime(Optional<String> endDateTime) throws IllegalValueException {
        requireNonNull(endDateTime);
        return endDateTime.isPresent() ? Optional.of(parseEndDateTime(endDateTime.get())) : Optional.empty();
    }
}
