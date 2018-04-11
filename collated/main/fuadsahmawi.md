# fuadsahmawi
###### \java\seedu\address\logic\commands\AddReminderCommand.java
``` java
/**
 * Adds a reminder to the Calendar.
 */
public class AddReminderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "+reminder";
    public static final String COMMAND_ALIAS = "+r";
    public static final String COMMAND_ALIAS_2 = "addreminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to Calendar. "
            + "Parameters: "
            + PREFIX_REMINDER_TEXT + "TEXT "
            + PREFIX_DATE + "START_DATETIME "
            + PREFIX_END_DATE + "END_DATETIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_TEXT + " do homework "
            + PREFIX_DATE + " tonight 8pm "
            + PREFIX_END_DATE + " tonight 10pm";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s "
            + "Disclaimer: If date & time parsed wrongly, delete reminder and refer to User Guide for correct format"
            + " of date and time";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the Calendar";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder}
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteReminderCommand.java
``` java
/**
 * Deletes a reminder identified using its title in the calendar
 */
public class DeleteReminderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "-reminder";
    public static final String COMMAND_ALIAS = "-r";
    public static final String COMMAND_ALIAS_2 = "deletereminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by its title & start time in the calendar.\n"
            + "Parameters: REMINDER_TITLE & START_DATETIME\n"
            + "Example: " + COMMAND_WORD + "text/Eat pills d/tmr 8pm";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private Index targetIndex;

    private String dateTime;

    private ReminderTextPredicate predicate;

    private Reminder reminderToDelete;

    public DeleteReminderCommand(ReminderTextPredicate predicate, String dateTime) {
        this.predicate = predicate;
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(reminderToDelete);
        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        model.updateFilteredReminderList(predicate);
        List<Reminder> lastShownList = model.getFilteredReminderList();
        targetIndex = Index.fromOneBased(1);
        if (lastShownList.size() > 1) {
            for (Reminder reminder : lastShownList) {
                if (reminder.getDateTime().toString().equals(dateTime)) {
                    reminderToDelete = reminder;
                }
            }
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_TEXT_DATE);
            }

            reminderToDelete = lastShownList.get(targetIndex.getZeroBased());
        }
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/KEYWORD [MORE_KEYWORDS]... or t/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie";

    public static final String MESSAGE_NOT_EDITED = "A keyword to find name or tag must be provided.";

    private TagContainsKeywordsPredicate predicateT = null;
    private NameContainsKeywordsPredicate predicateN = null;

    public FindCommand(NameContainsKeywordsPredicate predicateName) {
        this.predicateN = predicateName;
    }

    public FindCommand(TagContainsKeywordsPredicate predicate) {
        this.predicateT = predicate;
    }

    @Override
    public CommandResult execute() {
        if (predicateT == null) {
            model.updateFilteredPersonList(predicateN);
        } else {
            model.updateFilteredPersonList(predicateT);
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (this.predicateT == null) {
            return other == this // short circuit if same object
                    || (other instanceof FindCommand // instanceof handles nulls
                    && this.predicateN.equals(((FindCommand) other).predicateN)); // state check
        } else {
            return other == this // short circuit if same object
                    || (other instanceof FindCommand // instanceof handles nulls
                    && this.predicateT.equals(((FindCommand) other).predicateT)); // state check
        }
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class FindPersonDescriptor {
        private String[] nameKeywords;
        private String[] tagKeywords;

        public FindPersonDescriptor() {
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.nameKeywords, this.tagKeywords);
        }

        public void setNameKeywords(String name) {
            this.nameKeywords = name.split("\\s+");
            ;
        }

        public void setTagKeywords(String tags) {
            this.tagKeywords = tags.split("\\s+");
        }

        public String[] getNameKeywords() {
            return this.nameKeywords;
        }

        public String[] getTagKeyWords() {
            return this.tagKeywords;
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddReminderCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_TEXT, PREFIX_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_TEXT, PREFIX_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        if (nattyDateAndTimeParser(argMultimap.getValue(PREFIX_DATE).get()).get().compareTo(
                nattyDateAndTimeParser(argMultimap.getValue(PREFIX_END_DATE).get()).get()) > 0
                || nattyDateAndTimeParser(argMultimap.getValue(PREFIX_END_DATE).get()).get().compareTo(
                        LocalDateTime.now()) < 0
                || nattyDateAndTimeParser(argMultimap.getValue(PREFIX_DATE).get()).get().compareTo(
                        LocalDateTime.now()) < 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        try {
            ReminderText reminderText = ParserUtil.parseReminderText(argMultimap.getValue(PREFIX_REMINDER_TEXT)).get();
            DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE)).get();
            EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATE)).get();
            Reminder reminder = new Reminder(reminderText, dateTime, endDateTime);
            return new AddReminderCommand(reminder);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\DeleteReminderCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteReminderCommand object
 */
public class DeleteReminderCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns an DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_TEXT, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_TEXT, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }

        String reminderText = argMultimap.getValue(PREFIX_REMINDER_TEXT).get();
        String dateTime = argMultimap.getValue(PREFIX_DATE).get();
        LocalDateTime localDateTime = nattyDateAndTimeParser(dateTime).get();
        dateTime = properReminderDateTimeFormat(localDateTime);
        String trimmedArgs = reminderText.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new DeleteReminderCommand(new ReminderTextPredicate(Arrays.asList(nameKeywords)), dateTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        FindPersonDescriptor findPersonDescriptor = new FindPersonDescriptor();

        argMultimap.getValue(PREFIX_NAME).ifPresent(findPersonDescriptor::setNameKeywords);
        argMultimap.getValue(PREFIX_TAG).ifPresent(findPersonDescriptor::setTagKeywords);


        if (!findPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_EDITED);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return new FindCommand(
                    new NameContainsKeywordsPredicate(Arrays.asList(findPersonDescriptor.getNameKeywords())));
        } else {
            return new FindCommand(
                    new TagContainsKeywordsPredicate(Arrays.asList(findPersonDescriptor.getTagKeyWords())));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

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

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a reminder to CollegeZone.
     * @throws DuplicateReminderException if an equivalent reminder already exists.
     */
    public void addReminder (Reminder r) throws DuplicateReminderException {
        reminders.add(r);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ReminderNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeReminder(Reminder key) throws ReminderNotFoundException {
        if (reminders.remove(key)) {
            return true;
        } else {
            throw new ReminderNotFoundException();
        }
    }
    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void updateReminder(Reminder target, Reminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReminder);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        reminders.setReminder(target, editedReminder);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addReminder(Reminder reminder) throws DuplicateReminderException {
        addressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Reminder> getFilteredReminderList() {
        return FXCollections.unmodifiableObservableList(filteredReminders);
    }

    @Override
    public void updateFilteredReminderList(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }

    @Override
    public synchronized void deleteReminder(Reminder reminder) throws ReminderNotFoundException {
        addressBook.removeReminder(reminder);
        indicateAddressBookChanged();
    }
    /*
    @Override
    public void updateReminder(Reminder target, Reminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, editedReminder);

        addressBook.updateReminder(target, editedReminder);
        indicateAddressBookChanged();
    }
    */
}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */

public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Iterator<Tag> ir = person.getTags().iterator();
        StringBuilder tag = new StringBuilder();
        while (ir.hasNext()) {
            tag.append(ir.next().tagName);
            tag.append(" ");
        }

        String tagS = tag.toString();

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagS, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<Reminder> getReminderList();

}
```
###### \java\seedu\address\model\reminder\EndDateTime.java
``` java
/**
 * Represents a Reminder's end date and time in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndDateTime(String)}
 */
public class EndDateTime {


    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS =
            "EndDateTime must be a valid date and time";
    public final String endDateTime;

    /**
     * Constructs a {@code EndDateTime}.
     *
     * @param endDateTime A valid endDateTime number.
     */
    public EndDateTime(String endDateTime) {
        if (endDateTime.equals("")) {
            this.endDateTime = "";
        } else {
            checkArgument(isValidEndDateTime(endDateTime), MESSAGE_END_DATE_TIME_CONSTRAINTS);
            LocalDateTime localEndDateTime = nattyDateAndTimeParser(endDateTime).get();
            this.endDateTime = properReminderDateTimeFormat(localEndDateTime);
        }
    }

    /**
     * Returns true if a given string is a valid person endDateTime number.
     */
    public static boolean isValidEndDateTime(String test) {
        Optional<LocalDateTime> localEndDateTime = nattyDateAndTimeParser(test);
        if (localEndDateTime.isPresent()) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.endDateTime.equals(((EndDateTime) other).endDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return endDateTime.hashCode();
    }
}
```
###### \java\seedu\address\model\reminder\exceptions\DuplicateReminderException.java
``` java
/**
 * Signals that the operation will result in duplicate Goal objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation will result in duplicate reminders");
    }
}
```
###### \java\seedu\address\model\reminder\exceptions\ReminderNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\reminder\Reminder.java
``` java
/**
 * Represents a Reminder
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {

    private final ReminderText reminderText;
    private final DateTime dateTime;
    private final EndDateTime endDateTime;

    /**
     * Every field must be present and not null.
     */

    public Reminder(ReminderText reminderText, DateTime dateTime, EndDateTime endDateTime) {
        requireAllNonNull(reminderText, dateTime);
        this.reminderText = reminderText;
        this.dateTime = dateTime;
        this.endDateTime = endDateTime;
    }

    public ReminderText getReminderText() {
        return reminderText;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getReminderText().equals(this.getReminderText())
                && otherReminder.getDateTime().equals(this.getDateTime())
                && otherReminder.getEndDateTime().equals(this.getEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(reminderText, dateTime, endDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Reminder: ")
                .append(getReminderText())
                .append(" Date & Time: ")
                .append(getDateTime())
                .append(" End Date & Time: ")
                .append(getEndDateTime());

        return builder.toString();
    }
}
```
###### \java\seedu\address\model\reminder\ReminderText.java
``` java
/**
 * Represents a Reminder's text in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidReminderText(String)}
 */
public class ReminderText {

    public static final String MESSAGE_REMINDER_TEXT_CONSTRAINTS =
            "Reminder text can be any expression that are not just whitespaces.";
    public static final String REMINDER_TEXT_VALIDATION_REGEX = "^(?!\\s*$).+";
    public final String reminderText;

    /**
     * Constructs a {@code ReminderText}.
     *
     * @param reminderText A valid reminder text.
     */
    public ReminderText(String reminderText) {
        requireNonNull(reminderText);
        checkArgument(isValidGoalText(reminderText), MESSAGE_REMINDER_TEXT_CONSTRAINTS);
        this.reminderText = reminderText;
    }

    /**
     * Returns true if a given string is a valid reminder text.
     */
    public static boolean isValidReminderText(String test) {
        return test.matches(REMINDER_TEXT_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid reminder text.
     */
    public static boolean isValidGoalText(String test) {
        return test.matches(REMINDER_TEXT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return reminderText;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.goal.GoalText // instanceof handles nulls
                && this.reminderText.equals(((
                        seedu.address.model.reminder.ReminderText) other).reminderText)); // state check
    }

    @Override
    public int hashCode() {
        return reminderText.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\ReminderTextPredicate.java
``` java
/**
 * Tests that a {@code Reminder}'s {@code ReminderText} matches any of the keywords given.
 */
public class ReminderTextPredicate implements Predicate<Reminder> {
    private final List<String> keywords;

    public ReminderTextPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Reminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getReminderText().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderTextPredicate // instanceof handles nulls
                && this.keywords.equals(((ReminderTextPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\reminder\UniqueReminderList.java
``` java
/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 */
public class UniqueReminderList implements Iterable<Reminder> {

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a reminder to the list.
     *
     * @throws DuplicateReminderException if the reminder to add is a duplicate of an existing reminder in the list.
     */
    public void add(Reminder toAdd) throws DuplicateReminderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the reminder {@code target} in the list with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if the replacement is equivalent to another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void setReminder(Reminder target, Reminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.equals(editedReminder) && internalList.contains(editedReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, editedReminder);
    }

    /**
     * Removes the equivalent reminder from the list.
     *
     * @throws ReminderNotFoundException if no such reminder could be found in the list.
     */
    public boolean remove(Reminder toRemove) throws ReminderNotFoundException {
        requireNonNull(toRemove);
        final boolean reminderFoundAndDeleted = internalList.remove(toRemove);
        if (!reminderFoundAndDeleted) {
            throw new ReminderNotFoundException();
        }
        return reminderFoundAndDeleted;
    }

    public void setReminders(UniqueReminderList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setReminders(List<Reminder> reminders) throws DuplicateReminderException {
        requireAllNonNull(reminders);
        final UniqueReminderList replacement = new UniqueReminderList();
        for (final Reminder reminder : reminders) {
            replacement.add(reminder);
        }
        setReminders(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReminderList // instanceof handles nulls
                && this.internalList.equals(((UniqueReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedReminder.java
``` java
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    @XmlElement(required = true)
    private String reminderText;
    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String endDateTime;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Constructs an {@code XmlAdaptedReminder} with the given person details.
     */
    public XmlAdaptedReminder(String reminderText, String dateTime, String endDateTime) {
        this.reminderText = reminderText;
        this.dateTime = dateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(Reminder source) {
        reminderText = source.getReminderText().toString();
        dateTime = source.getDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        if (this.reminderText == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReminderText.class.getSimpleName()));
        }
        if (!ReminderText.isValidReminderText(this.reminderText)) {
            throw new IllegalValueException(ReminderText.MESSAGE_REMINDER_TEXT_CONSTRAINTS);
        }
        final ReminderText reminderText = new ReminderText(this.reminderText);

        if (this.dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(this.dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATE_TIME_CONSTRAINTS);
        }
        final DateTime dateTime = new DateTime(this.dateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(this.endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        final EndDateTime endDateTime = new EndDateTime(this.endDateTime);

        return new Reminder(reminderText, dateTime, endDateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReminder)) {
            return false;
        }

        XmlAdaptedReminder otherPerson = (XmlAdaptedReminder) other;
        return Objects.equals(reminderText, otherPerson.reminderText)
                && Objects.equals(dateTime, otherPerson.dateTime)
                && Objects.equals(endDateTime, otherPerson.endDateTime);
    }
}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarView;

    private ObservableList<Reminder> reminderList;

    private ObservableList<Person> personList;

    public CalendarPanel(ObservableList<Reminder> reminderList, ObservableList<Person> personList) {
        super(FXML);

        this.reminderList = reminderList;
        this.personList = personList;

        calendarView = new CalendarView();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
        updateCalendar();
        registerAsAnEventHandler(this);


    }

    @Subscribe
    private void handleNewCalendarEvent(AddressBookChangedEvent event) {
        reminderList = event.data.getReminderList();
        personList = event.data.getPersonList();
        Platform.runLater(this::updateCalendar);
    }




    /**
     * Updates the Calendar with Reminders that are already added
     */
    private void updateCalendar() {
        setDateAndTime();
        CalendarSource myCalendarSource = new CalendarSource("Reminders and Meetups");
        Calendar calendarRDue = new Calendar("Reminders Already Due");
        Calendar calendarRNotDue = new Calendar("Reminders Not Due");
        Calendar calendarM = new Calendar("Meetups");
        calendarRDue.setStyle(Calendar.Style.getStyle(4));
        calendarRDue.setLookAheadDuration(Duration.ofDays(365));
        calendarRNotDue.setStyle(Calendar.Style.getStyle(1));
        calendarRNotDue.setLookAheadDuration(Duration.ofDays(365));
        calendarM.setStyle(Calendar.Style.getStyle(3));
        myCalendarSource.getCalendars().add(calendarRDue);
        myCalendarSource.getCalendars().add(calendarRNotDue);
        myCalendarSource.getCalendars().add(calendarM);
        for (Reminder reminder : reminderList) {
            LocalDateTime ldtstart = nattyDateAndTimeParser(reminder.getDateTime().toString()).get();
            LocalDateTime ldtend = nattyDateAndTimeParser(reminder.getEndDateTime().toString()).get();
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(ldtend)) {
                calendarRNotDue.addEntry(new Entry(
                        reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
            } else {
                calendarRDue.addEntry(new Entry(reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
            }
        }
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
    private void setDateAndTime() {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

}
```
