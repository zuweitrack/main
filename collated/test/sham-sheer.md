# sham-sheer
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortPersons(Index index) throws IndexOutOfBoundsException {
            fail("This method should not be called.");
        }

        public void deleteMeetDate(Person person) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

```
###### /java/seedu/address/logic/commands/DeleteMeetCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteMeetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteMeetCommand deleteMeetCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteMeetCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeetDate(personToDelete);

        assertCommandSuccess(deleteMeetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteMeetCommand deleteMeetCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteMeetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*@Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteMeetCommand deleteMeetCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteMeetCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteMeetDate(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteMeetCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteMeetCommand deleteMeetCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteMeetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteMeetCommand deleteMeetCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteMeetCommand.execute();
        undoRedoStack.push(deleteMeetCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteMeetDate(personToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteMeetCommand deleteMeetCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteMeetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteMeetCommand deleteMeetCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteMeetCommand.execute();
        undoRedoStack.push(deleteMeetCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteMeetDate(personToDelete);
        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteMeetCommand deleteFirstMeetCommand = prepareCommand(INDEX_FIRST_PERSON);
        DeleteMeetCommand deleteSecondMeetCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstMeetCommand.equals(deleteFirstMeetCommand));

        // same values -> returns true
        DeleteMeetCommand deleteFirstMeetCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstMeetCommand.equals(deleteFirstMeetCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstMeetCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstMeetCommand.equals(deleteFirstMeetCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstMeetCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstMeetCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstMeetCommand.equals(deleteSecondMeetCommand));
    }

    /**
     * Returns a {@code DeleteMeetCommand} with the parameter {@code index}.
     */
    private DeleteMeetCommand prepareCommand(Index index) {
        DeleteMeetCommand deleteMeetCommand = new DeleteMeetCommand(index);
        deleteMeetCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteMeetCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
```
###### /java/seedu/address/logic/commands/MeetCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class MeetCommandTest {

    public static final String MEETDATE_STUB = "14/04/2018";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_addMeetDateUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMeetDate(MEETDATE_STUB).build();

        MeetCommand meetCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getMeetDate().value);

        String expectedMessage = String.format(MeetCommand.MESSAGE_ADD_MEETDATE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(meetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMeetDateUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMeetDate("").build();

        MeetCommand meetCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getMeetDate().toString());

        String expectedMessage = String.format(MeetCommand.MESSAGE_DELETE_MEETDATE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(meetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withMeetDate(MEETDATE_STUB).build();

        MeetCommand meetCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getMeetDate().value);

        String expectedMessage = String.format(MeetCommand.MESSAGE_ADD_MEETDATE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(meetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MeetCommand meetCommand = prepareCommand(outOfBoundIndex, VALID_MEETDATE_BOB);

        assertCommandFailure(meetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MeetCommand meetCommand = prepareCommand(outOfBoundIndex, VALID_MEETDATE_BOB);

        assertCommandFailure(meetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMeetDate(MEETDATE_STUB).build();
        MeetCommand meetCommand = prepareCommand(INDEX_FIRST_PERSON, MEETDATE_STUB);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // meet date -> first person meet date changed
        meetCommand.execute();
        undoRedoStack.push(meetCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MeetCommand meetCommand = prepareCommand(outOfBoundIndex, "");

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(meetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        MeetCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, MEETDATE_STUB);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withMeetDate(MEETDATE_STUB).build();
        // meet date -> modifies second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }



    @Test
    public void equals() {
        String testDate = "15/03/2018";
        String testDateTwo = "16/03/2018";
        final MeetCommand standardCommand = new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDate));

        // same values -> returns true
        MeetCommand commandWithSameValues = new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDate));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(INDEX_SECOND_PERSON, new Meet(testDate))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(INDEX_FIRST_PERSON, new Meet(testDateTwo))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private MeetCommand prepareCommand(Index index, String date) {
        MeetCommand meetCommand = new MeetCommand(index, new Meet(date));
        meetCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return meetCommand;
    }
}

```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index sortType = INDEX_SORT_LEVEL_OF_FRIENDSHIP;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_LEVEL_OF_FRIENDSHIP, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList2_success() throws Exception {
        Index sortType = INDEX_SORT_MEET_DATE;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_MEET_DATE, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList3_success() throws Exception {
        Index sortType = INDEX_SORT_BIRTHDAY;
        SortCommand sortCommand = prepareCommand(sortType);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORTED_SUCCESS_BIRTHDAY, sortType);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(sortType);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(4);
        SortCommand sortCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(sortCommand, model, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    private SortCommand prepareCommand(Index index) {
        SortCommand sortCommand = new SortCommand(index);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### /java/seedu/address/logic/parser/DeleteMeetCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteMeetCommandParserTest {

    private DeleteMeetCommandParser parser = new DeleteMeetCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMeetCommand() {
        assertParseSuccess(parser, "1", new DeleteMeetCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/MeetCommandParserTest.java
``` java
public class MeetCommandParserTest {
    private MeetCommandParser parser = new MeetCommandParser();
    private final String nonEmptyDate = "15/03/2018";

    @Test
    public void parse_indexSpecified_success() throws Exception {
        //have a date
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DATE.toString() + nonEmptyDate;
        MeetCommand expectedCommand = new MeetCommand(INDEX_FIRST_PERSON, new Meet(nonEmptyDate));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, MeetCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, MeetCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }

}
```
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
``` java
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new SortCommand(INDEX_SORT_LEVEL_OF_FRIENDSHIP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/person/MeetTest.java
``` java
public class MeetTest {

    @Test
    public void equals() {
        Meet meet = new Meet("14/01/2018");

        // same object -> return true
        assertTrue(meet.equals(meet));

        // same values -> returns true
        Meet meetDuplicate = new Meet(meet.value);
        assertTrue(meet.equals(meetDuplicate));

        // different types -> returns false
        assertFalse(meet.equals("14/01/2018"));

        // null -> returns false
        assertFalse(meet.equals(null));

        // different meet -> returns false;
        Meet differentMeet = new Meet("15/01/2018");
        assertFalse(meet.equals(differentMeet));
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withMeetDate(String meetDate) {
        this.meetDate = new Meet(meetDate);
        return this;
    }

    public Person build() {
        return new Person(name, phone, birthday, levelOfFriendship, unitNumber, ccas, meetDate, tags);
    }

}
```
