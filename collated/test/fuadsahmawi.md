# fuadsahmawi
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteReminder(Reminder target) throws ReminderNotFoundException {
            fail("This method should not be called.");
        }

<<<<<<< HEAD
=======
        public void deleteMeetDate(Person person) throws PersonNotFoundException {
            fail("This method should not be called.");
        }
>>>>>>> d5334ffb867978561af90038a7eb79997bafa6fc
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\AddReminderCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddReminderCommand}.
 */
public class AddReminderCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalReminderAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() throws Exception {
        Reminder validReminder = new ReminderBuilder().withReminderText(VALID_REMINDER_TEXT_B).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReminder(validReminder);

        assertCommandSuccess(prepareCommand(validReminder, model), model,
                String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder reminderInList = model.getAddressBook().getReminderList().get(0);
        assertCommandFailure(prepareCommand(reminderInList, model),
                model, AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    /**
     * Generates a new {@code AddRemminderCommand} which upon execution, adds {@code reminder} into the {@code model}.
     */
    private AddReminderCommand prepareCommand(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddReminderCommandTest.java
``` java
public class AddReminderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }

    /*
    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddReminderCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        getAddReminderCommandForReminder(validReminder, modelStub).execute();
    }
    */

    @Test
    public void equals() {
        Reminder a = new ReminderBuilder().withReminderText("A").build();
        Reminder b = new ReminderBuilder().withReminderText("B").build();
        AddReminderCommand addReminderACommand = new AddReminderCommand(a);
        AddReminderCommand addReminderBCommand = new AddReminderCommand(b);

        // same object -> returns true
        assertTrue(addReminderACommand.equals(addReminderACommand));

        // same values -> returns true
        AddReminderCommand addReminderACommandCopy = new AddReminderCommand(a);
        assertTrue(addReminderACommand.equals(addReminderACommandCopy));

        // different types -> returns false
        assertFalse(addReminderACommand.equals(1));

        // null -> returns false
        assertFalse(addReminderACommand.equals(null));

        // different reminder -> returns false
        assertFalse(addReminderACommand.equals(addReminderBCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminderCommand getAddReminderCommandForReminder(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateReminderException when trying to add a reminder.
     */
    private class ModelStubThrowingDuplicateReminderException extends AddCommandTest.ModelStub {
        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends AddCommandTest.ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }
    }
}
```
###### \java\seedu\address\logic\commands\ReminderCommandTestUtil.java
``` java
/**
 * Contains helper methods for testing reminder commands.
 */
public class ReminderCommandTestUtil {
    public static final String VALID_REMINDER_TEXT_A = "Medical Appointment";
    public static final String VALID_REMINDER_TEXT_B = "CG2271 Finals";
    public static final String VALID_REMINDER_START_DATE_TIME_STRING_A = "2018-03-03 10:30";
    public static final String VALID_REMINDER_START_DATE_TIME_STRING_B = "2018-03-03 10:31";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_A = "2018-03-03 12:30";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_B = "";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_C = "14/10/2018 3pm";
    public static final String VALID_REMINDER_END_DATE_TIME_STRING_D = "10/10/2018 4pm";
    public static final String REMINDER_TEXT_DESC_A = " " + PREFIX_REMINDER_TEXT + VALID_REMINDER_TEXT_A;
    public static final String REMINDER_TEXT_DESC_B = " " + PREFIX_REMINDER_TEXT + VALID_REMINDER_TEXT_B;
    public static final String REMINDER_START_DATE_TIME_DESC_A = " " + PREFIX_DATE
            + VALID_REMINDER_START_DATE_TIME_STRING_A;
    public static final String REMINDER_START_DATE_TIME_DESC_B = " " + PREFIX_DATE
            + VALID_REMINDER_START_DATE_TIME_STRING_B;
    public static final String REMINDER_END_DATE_TIME_DESC_A = " " + PREFIX_END_DATE
            + VALID_REMINDER_END_DATE_TIME_STRING_A;
    public static final String REMINDER_END_DATE_TIME_DESC_B = " " + PREFIX_END_DATE
            + VALID_REMINDER_END_DATE_TIME_STRING_B;

    public static final String INVALID_REMINDER_TEXT_DESC = " " + PREFIX_REMINDER_TEXT + "";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final LocalDateTime VALID_REMINDER_START_DATE_TIME_A;
    public static final LocalDateTime VALID_REMINDER_START_DATE_TIME_B;


    static {
        VALID_REMINDER_START_DATE_TIME_A = getLocalDateTimeFromString(VALID_REMINDER_START_DATE_TIME_STRING_A);
        VALID_REMINDER_START_DATE_TIME_B = getLocalDateTimeFromString(VALID_REMINDER_START_DATE_TIME_STRING_B);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered REMINDER list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Reminder> expectedFilteredList = new ArrayList<>(actualModel.getFilteredReminderList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredReminderList());
        }
    }

    /**
     * Deletes the first REMINDER in {@code model}'s list from {@code model}'s address book.
     */
    public static void deleteFirstReminder(Model model) {
        Reminder firstReminder = model.getFilteredReminderList().get(0);
        try {
            model.deleteReminder(firstReminder);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("Reminder in filtered list must exist in model.", pnfe);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddReminderCommandParserTest.java
``` java
public class AddReminderCommandParserTest {
    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing end date time prefix
        assertParseFailure(parser, REMINDER_TEXT_DESC_B + REMINDER_START_DATE_TIME_DESC_B, expectedMessage);

        // missing reminder text prefix
        assertParseFailure(parser, REMINDER_START_DATE_TIME_DESC_B + REMINDER_END_DATE_TIME_DESC_B,
                expectedMessage);

        // missing start date time prefix
        assertParseFailure(parser, REMINDER_TEXT_DESC_B + REMINDER_END_DATE_TIME_DESC_B,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid goal text
        assertParseFailure(parser, INVALID_REMINDER_TEXT_DESC + REMINDER_START_DATE_TIME_DESC_A
                + REMINDER_END_DATE_TIME_DESC_A,
                String.format(MESSAGE_INVALID_DATE_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + REMINDER_TEXT_DESC_B + REMINDER_START_DATE_TIME_DESC_B
                + REMINDER_END_DATE_TIME_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\ReminderBuilder.java
``` java
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_END_DATE_TIME = "2017-04-08 13:30";
    public static final String DEFAULT_REMINDER_TEXT = "go home play game";
    public static final String DEFAULT_START_DATE_TIME = "2017-04-08 12:30";

    private EndDateTime endDateTime;
    private ReminderText reminderText;
    private DateTime dateTime;

    public ReminderBuilder() {
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
        reminderText = new ReminderText(DEFAULT_REMINDER_TEXT);
        dateTime = new DateTime(DEFAULT_START_DATE_TIME);
    }

    /**
     * Initializes the GoalBuilder with the data of {@code goalToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        endDateTime = reminderToCopy.getEndDateTime();
        reminderText = reminderToCopy.getReminderText();
        dateTime = reminderToCopy.getDateTime();
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code ReminderText} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminderText(String reminderText) {
        this.reminderText = new ReminderText(reminderText);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Goal} that we are building.
     */
    public ReminderBuilder withDateTime(String startDateTime) {
        this.dateTime = new DateTime(startDateTime);
        return this;
    }

    public Reminder build() {
        return new Reminder(reminderText, dateTime, endDateTime);
    }
}
```
###### \java\seedu\address\testutil\ReminderUtil.java
``` java
/**
 * A utility class for Reminder.
 */
public class ReminderUtil {

    /**
     * Returns an addreminder command string for adding the {@code reminder}.
     */
    public static String getAddReminderCommand(Reminder reminder) {
        return AddReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code goal}'s details.
     */
    public static String getReminderDetails(Reminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER_TEXT + reminder.getReminderText().toString() + " ");
        sb.append(PREFIX_DATE + reminder.getDateTime().toString() + " ");
        sb.append(PREFIX_END_DATE + reminder.getEndDateTime().toString() + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalReminders.java
``` java
/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {
    public static final Reminder REMINDER_A = new ReminderBuilder()
            .withDateTime("2017-04-08 12:30")
            .withEndDateTime("2017-04-08 14:30")
            .withReminderText("go to the gym").build();
    public static final Reminder REMINDER_B = new ReminderBuilder()
            .withDateTime("2017-06-08 15:30")
            .withEndDateTime("2017-06-08 17:30")
            .withReminderText("medical appointment").build();
    public static final Reminder REMINDER_C = new ReminderBuilder()
            .withDateTime("2017-05-10 12:30")
            .withEndDateTime("2017-05-10 14:30")
            .withReminderText("cc").build();
    public static final Reminder REMINDER_D = new ReminderBuilder()
            .withDateTime("2018-04-08 12:30")
            .withEndDateTime("2018-04-08 14:30")
            .withReminderText("dd").build();
    public static final Reminder REMINDER_E = new ReminderBuilder()
            .withDateTime("2017-07-07 13:30")
            .withEndDateTime("2017-07-07 14:30")
            .withReminderText("ee").build();
    public static final Reminder REMINDER_F = new ReminderBuilder()
            .withDateTime("2017-03-10 09:30")
            .withEndDateTime("2017-03-10 10:30")
            .withReminderText("ff").build();

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical reminders.
     */
    public static AddressBook getTypicalReminderAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reminder reminder : getTypicalReminders()) {
            try {
                ab.addReminder(reminder);
            } catch (DuplicateReminderException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REMINDER_A, REMINDER_B, REMINDER_C, REMINDER_D, REMINDER_E, REMINDER_F));
    }
}
```
