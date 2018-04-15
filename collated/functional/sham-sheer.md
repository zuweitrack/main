# sham-sheer
###### /java/seedu/address/logic/CommandFormatListUtil.java
``` java
/**
 * Initialises and returns a list which contains different command formats
 */
public final class CommandFormatListUtil {
    private static ArrayList<String> commandFormatList;

    public static ArrayList<String> getCommandFormatList () {
        commandFormatList = new ArrayList<>();
        createCommandFormatList();
        return commandFormatList;
    }

    /**
     * Creates commandFormatList for existing commands
     */
    private static void createCommandFormatList() {
        commandFormatList.add(AddCommand.COMMAND_FORMAT);
        commandFormatList.add(AddGoalCommand.COMMAND_FORMAT);
        commandFormatList.add(AddReminderCommand.COMMAND_WORD);
        commandFormatList.add(ClearCommand.COMMAND_WORD);
        commandFormatList.add(CompleteGoalCommand.COMMAND_WORD);
        commandFormatList.add(DeleteCommand.COMMAND_WORD);
        commandFormatList.add(DeleteGoalCommand.COMMAND_WORD);
        commandFormatList.add(DeleteMeetCommand.COMMAND_WORD);
        commandFormatList.add(DeleteReminderCommand.COMMAND_ALIAS_2);
        commandFormatList.add(EditCommand.COMMAND_FORMAT);
        commandFormatList.add(EditGoalCommand.COMMAND_WORD);
        commandFormatList.add(ExitCommand.COMMAND_WORD);
        commandFormatList.add(FindCommand.COMMAND_FORMAT);
        commandFormatList.add(HelpCommand.COMMAND_WORD);
        commandFormatList.add(HistoryCommand.COMMAND_WORD);
        commandFormatList.add(ListCommand.COMMAND_WORD);
        commandFormatList.add(MeetCommand.COMMAND_WORD);
        commandFormatList.add(OngoingGoalCommand.COMMAND_WORD);
        commandFormatList.add(RateCommand.COMMAND_WORD);
        commandFormatList.add(RedoCommand.COMMAND_WORD);
        commandFormatList.add(SelectCommand.COMMAND_WORD);
        commandFormatList.add(SortCommand.COMMAND_WORD);
        commandFormatList.add(SortGoalCommand.COMMAND_WORD);
        commandFormatList.add(ShowLofCommand.COMMAND_WORD);
        commandFormatList.add(ThemeCommand.COMMAND_WORD);
        commandFormatList.add(UndoCommand.COMMAND_WORD);

        //sorting the commandFormatList
        Collections.sort(commandFormatList);
    }
}
```
###### /java/seedu/address/logic/commands/DeleteMeetCommand.java
``` java
/**
 * Removes the meet up set with a person using the person's displayed index from CollegeZone.
 */
public class DeleteMeetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "-meet";

    public static final String COMMAND_ALIAS = "-m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's meet date identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "You are not meeting %1$s anymore. ";

    private final Index targetIndex;

    private Person personToDelete;

    public DeleteMeetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(personToDelete);
        try {
            model.deleteMeetDate(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteMeetCommand) other).targetIndex) // state check
                && Objects.equals(this.personToDelete, ((DeleteMeetCommand) other).personToDelete));
    }


}
```
###### /java/seedu/address/logic/commands/MeetCommand.java
``` java
/**
 * Adds a meeting to CollegeZone.
 */
public class MeetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "meet";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the date of meetup for the person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01/04/2018";


    public static final String MESSAGE_ADD_MEETDATE_SUCCESS = "%1$s added for meet up! Check out your Calendar!";
    public static final String MESSAGE_DELETE_MEETDATE_SUCCESS = "You are not meeting %1$s anymore!!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person has already been set to have meeting.";

    private final Index targetIndex;
    private final Meet date;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param targetIndex of the person in the filtered person list you want to meet
     * @param date you want to meet the person
     */

    public MeetCommand(Index targetIndex, Meet date) {
        requireNonNull(targetIndex);
        requireNonNull(date);

        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getBirthday(),
                personToEdit.getLevelOfFriendship(), personToEdit.getUnitNumber(), personToEdit.getCcas(),
                date, personToEdit.getTags());
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !date.value.isEmpty() ? MESSAGE_ADD_MEETDATE_SUCCESS : MESSAGE_DELETE_MEETDATE_SUCCESS;
        return String.format(message, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetCommand)) {
            return false;
        }

        // state check
        MeetCommand e = (MeetCommand) other;
        return targetIndex.equals(e.targetIndex)
                && date.equals(e.date);
    }





}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sort the persons in CollegeZone based on the users parameters
 */
public class SortCommand extends UndoableCommand {

    public static final  String COMMAND_WORD = "sort";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid sort type: %1$s";

    public static final String MESSAGE_EMPTY_LIST = "CollegeZone student list is empty, There is nothing to sort!";

    public static final String MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP = "List sorted according to LOF!";

    public static final String MESSAGE_SORTED_SUCCESS_MEET_DATE = "List sorted according to your latest meet date!";

    public static final String MESSAGE_SORTED_SUCCESS_BIRTHDAY = "List sorted according to your latest birthday!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the person list identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    public SortCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortPersons(index);
        } catch (IndexOutOfBoundsException ioe) {
            throw new AssertionError("The index is out of bounds");
        }
        if (index.getOneBased() == 1) {
            return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP));
        }
        if (index.getOneBased() == 2) {
            return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_MEET_DATE));
        }
        return new CommandResult(String.format(MESSAGE_SORTED_SUCCESS_BIRTHDAY));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getOneBased() > 3) {
            throw new CommandException(String.format(SortCommand.MESSAGE_INVALID_COMMAND_FORMAT, index.getOneBased()));
        }
        if (lastShownList.size() == 0) {
            throw new CommandException(String.format(SortCommand.MESSAGE_EMPTY_LIST));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.index.equals(((SortCommand) other).index)); // state check
    }


}

```
###### /java/seedu/address/logic/parser/MeetCommandParser.java
``` java
/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class MeetCommandParser implements Parser {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MeetCommand}
     * and returns a {@code MeetCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MeetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Meet meetDate = ParserUtil.parseMeetDate(argMultimap.getValue(PREFIX_DATE)).get();
            return new MeetCommand(index, meetDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String unitNumber} into an {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unitNumber} is invalid.
     */
    public static Meet parseMeetDate(String meetDate) throws IllegalValueException {
        requireNonNull(meetDate);
        String trimmedMeetDate = meetDate.trim();
        if (!Meet.isValidDate(meetDate)) {
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

```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SortCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void sortPersons(Index index) throws IndexOutOfBoundsException {
        this.persons.sortPersons(index);
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void deleteMeetDate (Person person) throws PersonNotFoundException {
        addressBook.removeMeetFromPerson(person);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void sortPersons(Index index) throws IndexOutOfBoundsException {
        addressBook.sortPersons(index);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
```
###### /java/seedu/address/model/person/Meet.java
``` java
/**
 * Represents a Person's date of meeting in the address book.
 * Guarantees: immutable; is always valid
 */
public class Meet {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Make sure date is in this format: DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX =
            "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/"
                    + "(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\"
                    + "d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579]"
                    + "[26])00))))$";

    public final String value;

    public Meet(String meet) {
        requireNonNull(meet);
        if (meet.isEmpty()) {
            this.value = "";
        } else {
            checkArgument(isValidDate(meet), MESSAGE_DATE_CONSTRAINTS);
            this.value = meet;
        }
    }
    /**
     * Converts  date to seconds
     */
    public static long convertDateToSeconds(String date) {
        if (date == "") {
            return 0;
        }
        int day = Integer.parseInt(date.toString().substring(0,
                2));
        int month = Integer.parseInt(date.toString().substring(3,
                5));
        int year = Integer.parseInt(date.toString().substring(6,
                10));
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, day);
        long seconds = calendar.getTimeInMillis();
        return seconds;
    }

    /**
     * Converts meet date to a time that is relative to current date, for sorting purposes
     */
    public static long dateToInt(String date) {
        Calendar calendar = Calendar.getInstance();
        long longDate = convertDateToSeconds(date.toString());
        long currentDate = calendar.getTimeInMillis();
        long timeDiff = longDate - currentDate;
        if (timeDiff < 0) {
            return Long.MAX_VALUE;
        } else {
            return timeDiff;
        }
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meet // instanceof handles nulls
                && this.value.equals(((Meet) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();

    }
}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorting method
     */
    public void sortExecution(Index index, ObservableList<Person> internalList) {
        requireNonNull(index);
        if (index.getOneBased() > 3) {
            throw new IndexOutOfBoundsException();
        }
        if (index.getOneBased() == 1) {
            Comparator<Person> comparator = Comparator.comparingInt(Person::getLevelOfFriendshipInt);
            FXCollections.sort(internalList, comparator);
            FXCollections.reverse(internalList);
        }
        if (index.getOneBased() == 2) {
            Comparator<Person> comparator = Comparator.comparingLong(Person::getMeetDateInt);
            FXCollections.sort(internalList, comparator);
        }
        if (index.getOneBased() == 3) {
            Comparator<Person> comparator = Comparator.comparingLong(Person::getBirthdayInt);
            FXCollections.sort(internalList, comparator);
            FXCollections.reverse(internalList);
        }
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        final Meet meetDate = new Meet(this.meetDate);

```
###### /java/seedu/address/ui/CalendarPanel.java
``` java
        for (Person person : personList) {
            String meetDate = person.getMeetDate().toString();
            if (!meetDate.isEmpty()) {
                int day = Integer.parseInt(meetDate.substring(0,
                        2));
                int month = Integer.parseInt(meetDate.substring(3,
                        5));
                int year = Integer.parseInt(meetDate.substring(6,
                        10));
                calendarM.addEntry(new Entry("Meeting " + person.getName().toString(),
                        new Interval(LocalDate.of(year, month, day), LocalTime.of(12, 0),
                                LocalDate.of(year, month, day), LocalTime.of(13, 0))));
            }
        }
        calendarView.getCalendarSources().add(myCalendarSource);
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Sets the commandbox to completed command format if the entered substring of the command is valid
     * @param text is the command which is to be autocompleted
     */
    private void autocompleteCommand(String text) {
        ArrayList<String> commandFormatList = CommandFormatListUtil.getCommandFormatList();

        //retrieve the list of words which begin with text
        List<String> autocompleteCommandList = commandFormatList.stream()
                .filter(s -> s.startsWith(text))
                .collect(Collectors.toList());

        //replace input in text field with matched keyword
        if (!autocompleteCommandList.isEmpty()) {
            replaceText(autocompleteCommandList.get(0));
        }

    }

}
```
