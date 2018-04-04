# deborahlow97
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addGoal(Goal goal) throws DuplicateGoalException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Goal> getFilteredGoalList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredGoalList(Predicate<Goal> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGoal(Goal target) throws GoalNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateGoal(Goal target, Goal editedGoal)
                throws DuplicateGoalException {
            fail("This method should not be called.");
        }

        @Override
        public void updateGoalWithoutParameters(Goal target, Goal editedGoal)
                throws GoalNotFoundException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddGoalCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddGoalCommand}.
 */
public class AddGoalCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGoal_success() throws Exception {
        Goal validGoal = new GoalBuilder().withImportance(VALID_GOAL_IMPORTANCE_B).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGoal(validGoal);

        assertCommandSuccess(prepareCommand(validGoal, model), model,
                String.format(AddGoalCommand.MESSAGE_SUCCESS, validGoal), expectedModel);
    }

    @Test
    public void execute_duplicateGoal_throwsCommandException() {
        Goal goalInList = model.getAddressBook().getGoalList().get(0);
        assertCommandFailure(prepareCommand(goalInList, model), model, AddGoalCommand.MESSAGE_DUPLICATE_GOAL);
    }

    /**
     * Generates a new {@code AddGoalCommand} which upon execution, adds {@code goal} into the {@code model}.
     */
    private AddGoalCommand prepareCommand(Goal goal, Model model) {
        AddGoalCommand command = new AddGoalCommand(goal);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

```
###### \java\seedu\address\logic\commands\AddGoalCommandTest.java
``` java
public class AddGoalCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullGoal_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGoalCommand(null);
    }

    /*@Test
    public void execute_goalAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGoalAdded modelStub = new ModelStubAcceptingGoalAdded();
        Goal validGoal = new GoalBuilder().build();

        CommandResult commandResult = getAddGoalCommandForGoal(validGoal, modelStub).execute();

        assertEquals(String.format(AddGoalCommand.MESSAGE_SUCCESS, validGoal), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGoal), modelStub.goalsAdded);
    }

    @Test
    public void execute_duplicateGoal_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateGoalException();
        Goal validGoal = new GoalBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddGoalCommand.MESSAGE_DUPLICATE_GOAL);

        getAddGoalCommandForGoal(validGoal, modelStub).execute();
    }
    */

    @Test
    public void equals() {
        Goal a = new GoalBuilder().withGoalText("A").build();
        Goal b = new GoalBuilder().withGoalText("B").build();
        AddGoalCommand addGoalACommand = new AddGoalCommand(a);
        AddGoalCommand addGoalBCommand = new AddGoalCommand(b);

        // same object -> returns true
        assertTrue(addGoalACommand.equals(addGoalACommand));

        // same values -> returns true
        AddGoalCommand addGoalACommandCopy = new AddGoalCommand(a);
        assertTrue(addGoalACommand.equals(addGoalACommandCopy));

        // different types -> returns false
        assertFalse(addGoalACommand.equals(1));

        // null -> returns false
        assertFalse(addGoalACommand.equals(null));

        // different goal -> returns false
        assertFalse(addGoalACommand.equals(addGoalBCommand));
    }

    /**
     * Generates a new AddGoalCommand with the details of the given goal.
     */
    private AddGoalCommand getAddGoalCommandForGoal(Goal goal, Model model) {
        AddGoalCommand command = new AddGoalCommand(goal);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateGoalException when trying to add a goal.
     */
    private class ModelStubThrowingDuplicateGoalException extends AddCommandTest.ModelStub {
        @Override
        public void addGoal(Goal goal) throws DuplicateGoalException {
            throw new DuplicateGoalException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the goal being added.
     */
    private class ModelStubAcceptingGoalAdded extends AddCommandTest.ModelStub {
        final ArrayList<Goal> goalsAdded = new ArrayList<>();

        @Override
        public void addGoal(Goal goal) throws DuplicateGoalException {
            requireNonNull(goal);
            goalsAdded.add(goal);
        }
    }
}
```
###### \java\seedu\address\logic\commands\CompleteGoalCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * CompleteGoalCommand.
 */
public class CompleteGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_allPreSpecifiedFieldsUnfilteredList_success() throws Exception {
        Index indexLastGoal = Index.fromOneBased(model.getFilteredGoalList().size());
        Goal lastGoal = model.getFilteredGoalList().get(indexLastGoal.getZeroBased());

        GoalBuilder goalInList = new GoalBuilder(lastGoal);
        Goal completedGoal = goalInList.withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();

        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(indexLastGoal, descriptor);

        String expectedMessage = String.format(CompleteGoalCommand.MESSAGE_COMPLETE_GOAL_SUCCESS, completedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoalWithoutParameters(lastGoal, completedGoal);

        assertCommandSuccess(completeGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGoalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(completeGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal completedGoal = new GoalBuilder().build();
        Goal goalToEdit = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(completedGoal).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first goal completed
        completeGoalCommand.execute();
        undoRedoStack.push(completeGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal completed again
        expectedModel.updateGoalWithoutParameters(goalToEdit, completedGoal);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
        CompleteGoalCommand completeGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> completeGoalCommand not pushed into undoRedoStack
        assertCommandFailure(completeGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final CompleteGoalCommand standardCommand = prepareCommand(INDEX_FIRST_GOAL, DESC_GOAL_COMPLETED_C);

        // same values -> returns true
        CompleteGoalDescriptor copyDescriptor = new CompleteGoalDescriptor(DESC_GOAL_COMPLETED_C);
        CompleteGoalCommand commandWithSameValues = prepareCommand(INDEX_FIRST_GOAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CompleteGoalCommand(INDEX_SECOND_GOAL, DESC_GOAL_COMPLETED_C)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CompleteGoalCommand(INDEX_FIRST_GOAL, DESC_GOAL_COMPLETED_D)));
    }

    /**
     * Returns an {@code CompleteGoalCommand} with parameters {@code index} and {@code descriptor}
     */
    private CompleteGoalCommand prepareCommand(Index index, CompleteGoalDescriptor descriptor) {
        CompleteGoalCommand completeGoalCommand = new CompleteGoalCommand(index, descriptor);
        completeGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return completeGoalCommand;
    }
}

```
###### \java\seedu\address\logic\commands\DeleteGoalCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGoalCommand}.
 */
public class DeleteGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Goal goalToDelete = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        DeleteGoalCommand deleteGoalCommand = prepareCommand(INDEX_FIRST_GOAL);

        String expectedMessage = String.format(DeleteGoalCommand.MESSAGE_DELETE_GOAL_SUCCESS, goalToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGoal(goalToDelete);

        assertCommandSuccess(deleteGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        DeleteGoalCommand deleteGoalCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal goalToDelete = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        DeleteGoalCommand deleteGoalCommand = prepareCommand(INDEX_FIRST_GOAL);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first goal deleted
        deleteGoalCommand.execute();
        undoRedoStack.push(deleteGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal deleted again
        expectedModel.deleteGoal(goalToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        DeleteGoalCommand deleteGoalCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteGoalCommand not pushed into undoRedoStack
        assertCommandFailure(deleteGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteGoalCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_GOAL);
        DeleteGoalCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_GOAL);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGoalCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_GOAL);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different goal -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteGoalCommand} with the parameter {@code index}.
     */
    private DeleteGoalCommand prepareCommand(Index index) {
        DeleteGoalCommand deleteGoalCommand = new DeleteGoalCommand(index);
        deleteGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGoalCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoGoal(Model model) {
        model.updateFilteredGoalList(p -> false);

        assertTrue(model.getFilteredGoalList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\EditGoalCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditGoalCommand.
 */
public class EditGoalCommandTest {

    private Model model = new ModelManager(getTypicalGoalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Goal editedGoal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(editedGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoal(model.getFilteredGoalList().get(0), editedGoal);

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastGoal = Index.fromOneBased(model.getFilteredGoalList().size());
        Goal lastGoal = model.getFilteredGoalList().get(indexLastGoal.getZeroBased());

        GoalBuilder goalInList = new GoalBuilder(lastGoal);
        Goal editedGoal = goalInList.withGoalText(VALID_GOAL_TEXT_B).withImportance(VALID_GOAL_IMPORTANCE_B).build();

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(indexLastGoal, descriptor);

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGoal(lastGoal, editedGoal);

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, new EditGoalDescriptor());
        Goal editedGoal = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());

        String expectedMessage = String.format(EditGoalCommand.MESSAGE_EDIT_GOAL_SUCCESS, editedGoal);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editGoalCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGoalUnfilteredList_failure() {
        Goal firstGoal = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(firstGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_SECOND_GOAL, descriptor);

        assertCommandFailure(editGoalCommand, model, EditGoalCommand.MESSAGE_DUPLICATE_GOAL);
    }

    @Test
    public void execute_invalidGoalIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Goal editedGoal = new GoalBuilder().build();
        Goal goalToEdit = model.getFilteredGoalList().get(INDEX_FIRST_GOAL.getZeroBased());
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(editedGoal).build();
        EditGoalCommand editGoalCommand = prepareCommand(INDEX_FIRST_GOAL, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first goal edited
        editGoalCommand.execute();
        undoRedoStack.push(editGoalCommand);

        // undo -> reverts addressbook back to previous state and filtered goal list to show all goals
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first goal edited again
        expectedModel.updateGoal(goalToEdit, editedGoal);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGoalList().size() + 1);
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand editGoalCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editGoalCommand not pushed into undoRedoStack
        assertCommandFailure(editGoalCommand, model, Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final EditGoalCommand standardCommand = prepareCommand(INDEX_FIRST_GOAL, DESC_GOAL_A);

        // same values -> returns true
        EditGoalDescriptor copyDescriptor = new EditGoalDescriptor(DESC_GOAL_A);
        EditGoalCommand commandWithSameValues = prepareCommand(INDEX_FIRST_GOAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGoalCommand(INDEX_SECOND_GOAL, DESC_GOAL_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditGoalCommand(INDEX_FIRST_GOAL, DESC_GOAL_B)));
    }

    /**
     * Returns an {@code EditGoalCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditGoalCommand prepareCommand(Index index, EditGoalDescriptor descriptor) {
        EditGoalCommand editGoalCommand = new EditGoalCommand(index, descriptor);
        editGoalCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editGoalCommand;
    }
}
```
###### \java\seedu\address\logic\commands\GoalCommandTestUtil.java
``` java
/**
 * Contains helper methods for testing commands.
 */
public class GoalCommandTestUtil {

    public static final String VALID_GOAL_TEXT_A = "Make 10 new friends in university";
    public static final String VALID_GOAL_TEXT_B = "Drink 8 glasses of water everyday - stay hydrated!!";
    public static final String VALID_GOAL_IMPORTANCE_A = "1";
    public static final String VALID_GOAL_IMPORTANCE_B = "10";
    public static final String VALID_GOAL_START_DATE_TIME_STRING_A = "2018-03-03 10:30";
    public static final String VALID_GOAL_START_DATE_TIME_STRING_B = "2018-03-03 10:31";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_A = "2018-04-04 10:30";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_B = "";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_C = "14/10/2018 3pm";
    public static final String VALID_GOAL_END_DATE_TIME_STRING_D = "10/10/2018 4pm";
    public static final boolean VALID_GOAL_COMPLETION_A = true;
    public static final boolean VALID_GOAL_COMPLETION_B = false;
    public static final boolean VALID_GOAL_COMPLETION_C = true;
    public static final boolean VALID_GOAL_COMPLETION_D = true;
    public static final String GOAL_TEXT_DESC_A = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_A;
    public static final String GOAL_TEXT_DESC_B = " " + PREFIX_GOAL_TEXT + VALID_GOAL_TEXT_B;
    public static final String GOAL_IMPORTANCE_DESC_A = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_A;
    public static final String GOAL_IMPORTANCE_DESC_B = " " + PREFIX_IMPORTANCE + VALID_GOAL_IMPORTANCE_B;

    public static final String INVALID_IMPORTANCE_DESC = " " + PREFIX_IMPORTANCE + "-1";
    // negative numbers not allowed in importance

    public static final String INVALID_GOAL_TEXT_DESC = " " + PREFIX_GOAL_TEXT + "";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_A;
    public static final LocalDateTime VALID_GOAL_START_DATE_TIME_B;
    public static final EditGoalCommand.EditGoalDescriptor DESC_GOAL_A;
    public static final EditGoalCommand.EditGoalDescriptor DESC_GOAL_B;

    public static final CompleteGoalCommand.CompleteGoalDescriptor DESC_GOAL_COMPLETED_C;
    public static final CompleteGoalCommand.CompleteGoalDescriptor DESC_GOAL_COMPLETED_D;

    static {
        VALID_GOAL_START_DATE_TIME_A = getLocalDateTimeFromString(VALID_GOAL_START_DATE_TIME_STRING_A);
        VALID_GOAL_START_DATE_TIME_B = getLocalDateTimeFromString(VALID_GOAL_START_DATE_TIME_STRING_B);

        DESC_GOAL_A = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_A)
                .withGoalText(VALID_GOAL_TEXT_A).build();
        DESC_GOAL_B = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        DESC_GOAL_COMPLETED_C = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_C)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_C).build();
        DESC_GOAL_COMPLETED_D = new CompleteGoalDescriptorBuilder().withCompletion(VALID_GOAL_COMPLETION_D)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_D).build();
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
     * - the address book and the filtered goal list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Goal> expectedFilteredList = new ArrayList<>(actualModel.getFilteredGoalList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredGoalList());
        }
    }

    /**
     * Deletes the first GOAL in {@code model}'s list from {@code model}'s address book.
     */
    public static void deleteFirstGoal(Model model) {
        Goal firstGoal = model.getFilteredGoalList().get(0);
        try {
            model.deleteGoal(firstGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("Goal in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addGoal() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(GoalUtil.getAddGoalCommand(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_addGoalAliasOne() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(
                AddGoalCommand.COMMAND_ALIAS_1 + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_addGoalAliasTwo() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(
                AddGoalCommand.COMMAND_ALIAS_2 + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_editGoal() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_editGoalAliasOne() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_ALIAS_1 + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_editGoalAliasTwo() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_ALIAS_2 + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_deleteGoal() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_WORD + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_deleteGoalAliasOne() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_ALIAS_1 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_deleteGoalAliasTwo() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_ALIAS_2 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_completeGoal() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_WORD + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_completeGoalAliasOne() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_ALIAS_1 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_completeGoalAliasTwo() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_ALIAS_2 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }
}
```
###### \java\seedu\address\logic\parser\CompleteGoalCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CompleteGoalCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CompleteGoalCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CompleteGoalCommandParserTest {

    private CompleteGoalCommandParser parser = new CompleteGoalCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteGoalCommand() {
        CompleteGoalDescriptor completeGoalDescriptor = new CompleteGoalDescriptor();
        completeGoalDescriptor.setCompletion(new Completion(true));
        completeGoalDescriptor.setEndDateTime(new EndDateTime("today"));
        assertParseSuccess(parser, "1", new CompleteGoalCommand(INDEX_FIRST_GOAL, completeGoalDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteGoalCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DateTimeParserTest.java
``` java
public class DateTimeParserTest {

    private static final Optional<LocalDateTime> nonEmptyLocalDateTime;
    private static final Optional<LocalDateTime> emptyLocalDateTime;

    static {
        nonEmptyLocalDateTime = Optional.of(LocalDateTime
            .of(2018, Month.JANUARY, 1, 15, 0, 0));
        emptyLocalDateTime = Optional.empty();
    }
    @Test
    public void parse_validArgs_success() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("1/1/2018 3pm");
        LocalDateTime aLocalDateTime = LocalDateTime.of(2018, Month.JANUARY, 1, 15, 0,
                0);
        assertEquals(dateTimeParse, nonEmptyLocalDateTime);
    }

    @Test
    public void parse_invalidFormatArgs_failure() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("1/1/20183pm");
        assertNotEquals(dateTimeParse, nonEmptyLocalDateTime);
    }

    @Test
    public void parse_invalidArgs_returnsNull() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("!@!(KJEw");
        assertEquals(dateTimeParse, emptyLocalDateTime);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteGoalCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteGoalCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteGoalCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteGoalCommandParserTest {

    private DeleteGoalCommandParser parser = new DeleteGoalCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGoalCommand() {
        assertParseSuccess(parser, "1", new DeleteGoalCommand(INDEX_FIRST_GOAL));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGoalCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\EditGoalCommandParserTest.java
``` java
public class EditGoalCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGoalCommand.MESSAGE_USAGE);

    private EditGoalCommandParser parser = new EditGoalCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_GOAL_TEXT_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditGoalCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + GOAL_TEXT_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + GOAL_TEXT_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid importance
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC, Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // invalid importance followed by valid goal text
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC + GOAL_TEXT_DESC_B,
                Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // valid goal text followed by invalid importance.
        assertParseFailure(parser, "1" + GOAL_TEXT_DESC_B + INVALID_IMPORTANCE_DESC,
                Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // invalid goal text
        assertParseFailure(parser, "1" + GOAL_IMPORTANCE_DESC_B + INVALID_GOAL_TEXT_DESC,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

        // valid importance followed by invalid goal text.
        assertParseFailure(parser, "1" + INVALID_GOAL_TEXT_DESC + GOAL_IMPORTANCE_DESC_A,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

        // invalid importance followed by invalid goal text. Last invalid value is captured
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC + INVALID_GOAL_TEXT_DESC,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A + GOAL_IMPORTANCE_DESC_B;

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // goal text
        Index targetIndex = INDEX_THIRD_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A;
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // importance
        userInput = targetIndex.getOneBased() + GOAL_IMPORTANCE_DESC_A;
        descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_A).build();
        expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A + GOAL_IMPORTANCE_DESC_A
                + GOAL_TEXT_DESC_B + GOAL_IMPORTANCE_DESC_B;

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_B)
                .withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    /*
    @Test
    public void parse_invalidValueFollowedByValidValue_fail() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_GOAL;
        String userInput = targetIndex.getOneBased() + INVALID_IMPORTANCE_DESC + GOAL_TEXT_DESC_B;
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}
```
###### \java\seedu\address\model\goal\GoalTextTest.java
``` java
public class GoalTextTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GoalText(null));
    }

    @Test
    public void constructor_invalidGoalText_throwsIllegalArgumentException() {
        String invalidGoalText = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GoalText(invalidGoalText));
    }

    @Test
    public void isValidGoalText() {
        // null goal text
        Assert.assertThrows(NullPointerException.class, () -> GoalText.isValidGoalText(null));

        // blank goal text
        assertFalse(GoalText.isValidGoalText("")); // empty string
        assertFalse(GoalText.isValidGoalText(" ")); // spaces only

        // valid goal text
        assertTrue(GoalText.isValidGoalText("1"));
        assertTrue(GoalText.isValidGoalText("aaa0"));  // alphanumerical
        assertTrue(GoalText.isValidGoalText("!@$#()_+"));   // symbols only
        assertTrue(GoalText.isValidGoalText("-1.122ewk:!@|!+@!*~"));   // all kinds of symbols and alphanumerical
        assertTrue(GoalText.isValidGoalText("! 1 wq ")); // with spaces
        assertTrue(GoalText.isValidGoalText("            7"));  // spaces with a value
    }
}
```
###### \java\seedu\address\model\goal\ImportanceTest.java
``` java
public class ImportanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Importance(null));
    }

    @Test
    public void constructor_invalidImportance_throwsIllegalArgumentException() {
        String invalidImportance = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Importance(invalidImportance));
    }

    @Test
    public void isValidImportance() {
        // null importance
        Assert.assertThrows(NullPointerException.class, () -> Importance.isValidImportance(null));

        // blank importance
        assertFalse(Importance.isValidImportance("")); // empty string
        assertFalse(Importance.isValidImportance(" ")); // spaces only

        // invalid parts
        assertFalse(Importance.isValidImportance("22")); // invalid positive level of friendship
        assertFalse(Importance.isValidImportance("-1")); // invalid negative level of friendship
        assertFalse(Importance.isValidImportance("11")); // invalid positive level of friendship
        assertFalse(Importance.isValidImportance("a")); // invalid character
        assertFalse(Importance.isValidImportance("11a")); // invalid extra character and number
        assertFalse(Importance.isValidImportance("10b")); // invalid extra character
        assertFalse(Importance.isValidImportance("9*")); // '*' symbol
        assertFalse(Importance.isValidImportance("^")); // '^' symbol
        assertFalse(Importance.isValidImportance("0")); // invalid number
        assertFalse(Importance.isValidImportance("1.1")); // number in decimal

        // valid importance
        assertTrue(Importance.isValidImportance("1"));
        assertTrue(Importance.isValidImportance("10"));  // minimal
        assertTrue(Importance.isValidImportance("2"));   // alphabets only
        assertTrue(Importance.isValidImportance("5")); // special characters local part
        assertTrue(Importance.isValidImportance("7"));  // numeric local part and domain name
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // blank birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("12--1997")); // missing month part
        assertFalse(Birthday.isValidBirthday("--12-1998")); // missing date part
        assertFalse(Birthday.isValidBirthday("//12/1998")); // missing date part
        assertFalse(Birthday.isValidBirthday("12-12-")); // missing year part
        assertFalse(Birthday.isValidBirthday("12/12/")); // missing year part


        // invalid parts
        assertFalse(Birthday.isValidBirthday("32-Jan-2000")); // invalid day
        assertFalse(Birthday.isValidBirthday("33.01.2000")); // invalid day
        assertFalse(Birthday.isValidBirthday("20/20/2000")); // invalid month
        assertFalse(Birthday.isValidBirthday("20/13/1997")); // invalid month
        assertFalse(Birthday.isValidBirthday("29/Feb/2001")); // invalid due to leap year
        assertFalse(Birthday.isValidBirthday("31/04/2000")); // invalid day for month of April
        assertFalse(Birthday.isValidBirthday("31/Sep/2000")); // invalid day for month of September
        assertFalse(Birthday.isValidBirthday("31//01/2000")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("31..01..2000")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("20--2-1997")); // invalid birthday format
        assertFalse(Birthday.isValidBirthday("20*2*1997")); // invalid symbols
        assertFalse(Birthday.isValidBirthday("12 / 12 / 2012")); // contains spaces

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01/01/2000"));
        assertTrue(Birthday.isValidBirthday("01/Jan/2000"));  // using /
        assertTrue(Birthday.isValidBirthday("31.Jan.2000"));   // using .
        assertTrue(Birthday.isValidBirthday("01-12-2000")); // using -
        assertTrue(Birthday.isValidBirthday("28-Feb-2001"));  // valid date
    }
}
```
###### \java\seedu\address\model\person\CcaTest.java
``` java
public class CcaTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Cca(null));
    }

    @Test
    public void constructor_invalidCcaName_throwsIllegalArgumentException() {
        String invalidCcaName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Cca(invalidCcaName));
    }

    @Test
    public void isValidCcaName() {
        // null cca name
        Assert.assertThrows(NullPointerException.class, () -> Cca.isValidCcaName(null));

        // invalid cca name
        assertFalse(Cca.isValidCcaName("!3")); // contains '!'
        assertFalse(Cca.isValidCcaName("abc%")); // contains '%'
        assertFalse(Cca.isValidCcaName("abc-1")); // contains '-'
        assertFalse(Cca.isValidCcaName("abc@@@1")); // contains '@'

        // valid cca name
        assertTrue(Cca.isValidCcaName("Hackathon")); // alphabets
        assertTrue(Cca.isValidCcaName("Walkathon 2018")); // using .alphanumeric with spaces
        assertTrue(Cca.isValidCcaName("Basketball")); // valid alphabets
        assertTrue(Cca.isValidCcaName("Hackathon2018")); //alphanumeric
    }
}
```
###### \java\seedu\address\model\person\LevelOfFriendshipTest.java
``` java
public class LevelOfFriendshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LevelOfFriendship(null));
    }

    @Test
    public void constructor_invalidLevelOfFriendship_throwsIllegalArgumentException() {
        String invalidLevelOfFriendship = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new LevelOfFriendship(invalidLevelOfFriendship));
    }

    @Test
    public void isValidLevelOfFriendship() {
        // null level of friendship
        Assert.assertThrows(NullPointerException.class, () -> LevelOfFriendship.isValidLevelOfFriendship(null));

        // blank level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("")); // empty string
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship(" ")); // spaces only

        // invalid parts
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("22")); // invalid positive level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("-1")); // invalid negative level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("11")); // invalid positive level of friendship
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("a")); // invalid character
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("11a")); // invalid extra character and number
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("10b")); // invalid extra character
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("9*")); // '*' symbol
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("^")); // '^' symbol
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("0")); // invalid number
        assertFalse(LevelOfFriendship.isValidLevelOfFriendship("1.1")); // number in decimal

        // valid level of friendship
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("1"));
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("10"));  // minimal
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("2"));   // alphabets only
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("5")); // special characters local part
        assertTrue(LevelOfFriendship.isValidLevelOfFriendship("7"));  // numeric local part and domain name
    }
}
```
###### \java\seedu\address\model\person\UniqueCcaListTest.java
``` java
public class UniqueCcaListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCcaList uniqueCcaList = new UniqueCcaList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCcaList.asObservableList().remove(0);
    }
}

```
###### \java\seedu\address\model\person\UnitNumberTest.java
``` java
public class UnitNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UnitNumber(null));
    }

    @Test
    public void constructor_invalidUnitNumber_throwsIllegalArgumentException() {
        String invalidUnitNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new UnitNumber(invalidUnitNumber));
    }

    @Test
    public void isValidUnitNumber() {
        // null unit number
        Assert.assertThrows(NullPointerException.class, () -> UnitNumber.isValidUnitNumber(null));

        // invalid unit numbers
        assertFalse(UnitNumber.isValidUnitNumber("")); // empty string
        assertFalse(UnitNumber.isValidUnitNumber(" ")); // spaces only
        assertFalse(UnitNumber.isValidUnitNumber("#12222-1312414")); // long unit number
        assertFalse(UnitNumber.isValidUnitNumber("#1-1")); // one character only

        // valid unit numbers
        assertTrue(UnitNumber.isValidUnitNumber("#01-355"));
        assertTrue(UnitNumber.isValidUnitNumber("#1-12"));
        assertTrue(UnitNumber.isValidUnitNumber("#12-12"));

    }
}
```
###### \java\seedu\address\model\UniqueGoalListTest.java
``` java
public class UniqueGoalListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGoalList uniquePersonList = new UniqueGoalList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedGoalTest.java
``` java
public class XmlAdaptedGoalTest {
    private static final String INVALID_IMPORTANCE = "11";
    private static final String INVALID_GOAL_TEXT = " ";

    private static final String VALID_IMPORTANCE = GOAL_B.getImportance().toString();
    private static final String VALID_GOAL_TEXT = GOAL_B.getGoalText().toString();
    private static final String VALID_START_DATE_TIME = GOAL_B.getStartDateTime().toString();
    private static final String VALID_COMPLETION = GOAL_B.getCompletion().toString();
    private static final String VALID_END_DATE_TIME = GOAL_B.getEndDateTime().toString();

    @Test
    public void toModelType_validGoalDetails_returnsGoal() throws Exception {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(GOAL_B);
        assertEquals(GOAL_B, goal.toModelType());
    }

    @Test
    public void toModelType_invalidImportance_throwsIllegalValueException() {
        XmlAdaptedGoal goal =
                new XmlAdaptedGoal(INVALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_COMPLETION);
        String expectedMessage = Importance.MESSAGE_IMPORTANCE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullImportance_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(null, VALID_GOAL_TEXT, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Importance.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_invalidGoalText_throwsIllegalValueException() {
        XmlAdaptedGoal goal =
                new XmlAdaptedGoal(VALID_IMPORTANCE, INVALID_GOAL_TEXT, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_COMPLETION);
        String expectedMessage = GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullGoalText_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, null, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GoalText.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }


    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, null, VALID_END_DATE_TIME,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullCompletion_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Completion.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedGoal goal = new XmlAdaptedGoal(VALID_IMPORTANCE, VALID_GOAL_TEXT, VALID_START_DATE_TIME, null,
                VALID_COMPLETION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBookTest.java
``` java
    @Test
    public void toModelType_invalidGoalFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_GOAL_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_typicalGoalsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_GOALS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalGoals.getTypicalGoalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }
}
```
###### \java\seedu\address\testutil\CompleteGoalDescriptorBuilder.java
``` java
/**
 * A utility class to help with building CompleteGoalDescriptor objects.
 */
public class CompleteGoalDescriptorBuilder {

    private CompleteGoalDescriptor descriptor;

    public CompleteGoalDescriptorBuilder() {
        descriptor = new CompleteGoalDescriptor();
    }

    public CompleteGoalDescriptorBuilder(CompleteGoalDescriptor descriptor) {
        this.descriptor = new CompleteGoalDescriptor(descriptor);
    }

    /**
     * Returns an {@code CompleteGoalDescriptor} with fields containing {@code goal}'s details
     */
    public CompleteGoalDescriptorBuilder(Goal goal) {
        descriptor = new CompleteGoalDescriptor();
        descriptor.setCompletion(new Completion(true));
        descriptor.setEndDateTime(new EndDateTime("today"));
        //TODO
        //descriptor.setCompletion(goal.getCompletion());
        //descriptor.setEndDateTime(goal.getEndDateTime());
    }

    /**
     * Sets the {@code Completion} of the {@code CompleteGoalDescriptor} that we are building.
     */
    public CompleteGoalDescriptorBuilder withCompletion(boolean isCompleted) {
        descriptor.setCompletion(new Completion(isCompleted));
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code CompleteGoalDescriptor} that we are building.
     */
    public CompleteGoalDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setEndDateTime(new EndDateTime(endDateTime));
        return this;
    }

    public CompleteGoalDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\EditGoalDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditGoalDescriptor objects.
 */
public class EditGoalDescriptorBuilder {

    private EditGoalDescriptor descriptor;

    public EditGoalDescriptorBuilder() {
        descriptor = new EditGoalDescriptor();
    }

    public EditGoalDescriptorBuilder(EditGoalDescriptor descriptor) {
        this.descriptor = new EditGoalDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGoalDescriptor} with fields containing {@code goal}'s details
     */
    public EditGoalDescriptorBuilder(Goal goal) {
        descriptor = new EditGoalDescriptor();
        descriptor.setGoalText(goal.getGoalText());
        descriptor.setImportance(goal.getImportance());
    }

    /**
     * Sets the {@code GoalText} of the {@code EditGoalDescriptor} that we are building.
     */
    public EditGoalDescriptorBuilder withGoalText(String goalText) {
        descriptor.setGoalText(new GoalText(goalText));
        return this;
    }

    /**
     * Sets the {@code Importance} of the {@code EditGoalDescriptor} that we are building.
     */
    public EditGoalDescriptorBuilder withImportance(String importance) {
        descriptor.setImportance(new Importance(importance));
        return this;
    }

    public EditGoalDescriptor build() {
        return descriptor;
    }
}

```
###### \java\seedu\address\testutil\GoalBuilder.java
``` java
/**
 * A utility class to help with building Person objects.
 */
public class GoalBuilder {

    public static final boolean DEFAULT_COMPLETION = false;
    public static final String DEFAULT_END_DATE_TIME = "";
    public static final String DEFAULT_GOAL_TEXT = "er yea acadamic no la no la";
    public static final String DEFAULT_IMPORTANCE = "1";
    public static final String DEFAULT_START_DATE_TIME = "2017-04-08 12:30";

    private Completion completion;
    private EndDateTime endDateTime;
    private GoalText goalText;
    private Importance importance;
    private StartDateTime startDateTime;

    public GoalBuilder() {
        completion = new Completion(DEFAULT_COMPLETION);
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
        goalText = new GoalText(DEFAULT_GOAL_TEXT);
        importance = new Importance(DEFAULT_IMPORTANCE);
        startDateTime = new StartDateTime(getLocalDateTimeFromString(DEFAULT_START_DATE_TIME));
    }

    /**
     * Initializes the GoalBuilder with the data of {@code goalToCopy}.
     */
    public GoalBuilder(Goal goalToCopy) {
        completion = goalToCopy.getCompletion();
        endDateTime = goalToCopy.getEndDateTime();
        goalText = goalToCopy.getGoalText();
        importance = goalToCopy.getImportance();
        startDateTime = goalToCopy.getStartDateTime();
    }

    /**
     * Sets the {@code Completion} of the {@code Goal} that we are building.
     */
    public GoalBuilder withCompletion(Boolean completion) {
        this.completion = new Completion(completion);
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Goal} that we are building.
     */
    public GoalBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code GoalText} of the {@code Goal} that we are building.
     */
    public GoalBuilder withGoalText(String goalText) {
        this.goalText = new GoalText(goalText);
        return this;
    }

    /**
     * Sets the {@code Importance} of the {@code Goal} that we are building.
     */
    public GoalBuilder withImportance(String importance) {
        this.importance = new Importance(importance);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Goal} that we are building.
     */
    public GoalBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new StartDateTime(getLocalDateTimeFromString(startDateTime));
        return this;
    }

    public Goal build() {
        return new Goal(importance, goalText, startDateTime, endDateTime, completion);
    }

}

```
###### \java\seedu\address\testutil\GoalUtil.java
``` java
/**
 * A utility class for Goal.
 */
public class GoalUtil {

    /**
     * Returns an add goal command string for adding the {@code goal}.
     */
    public static String getAddGoalCommand(Goal goal) {
        return AddGoalCommand.COMMAND_WORD + " " + getGoalDetails(goal);
    }

    /**
     * Returns the part of command string for the given {@code goal}'s details.
     */
    public static String getGoalDetails(Goal goal) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_IMPORTANCE + goal.getImportance().value + " ");
        sb.append(PREFIX_GOAL_TEXT + goal.getGoalText().value + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalGoals.java
``` java
/**
 * A utility class containing a list of {@code Goal} objects to be used in tests.
 */
public class TypicalGoals {

    public static final Goal GOAL_A = new GoalBuilder().withCompletion(false)
            .withEndDateTime("").withGoalText("stay fit").withImportance("1")
            .withStartDateTime("2017-04-08 12:30").build();
    public static final Goal GOAL_B = new GoalBuilder().withCompletion(false)
            .withEndDateTime("").withGoalText("eat fruits daily").withImportance("2")
            .withStartDateTime("2017-05-08 12:30").build();
    public static final Goal GOAL_C = new GoalBuilder().withCompletion(true).withEndDateTime("2018-04-08 12:30")
            .withGoalText("aa").withImportance("7").withStartDateTime("2017-06-08 12:30").build();
    public static final Goal GOAL_D = new GoalBuilder().withCompletion(true).withEndDateTime("2018-04-08 12:31")
            .withGoalText("bb").withImportance("4").withStartDateTime("2017-06-08 12:31").build();
    public static final Goal GOAL_E = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("cc").withImportance("10").withStartDateTime("2017-06-08 12:32")
            .build();
    public static final Goal GOAL_F = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("dd").withImportance("3").withStartDateTime("2017-06-08 12:33").build();
    public static final Goal GOAL_G = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("ee").withImportance("8").withStartDateTime("2017-06-08 12:35")
            .build();

    // Manually added
    public static final Goal HOON = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("ff").withImportance("1").withStartDateTime("2017-06-08 12:36")
            .build();
    public static final Goal IDA = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("gg").withImportance("3").withStartDateTime("2017-06-08 12:38")
            .build();
    public static final Goal JAKE = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("hii").withImportance("3").withStartDateTime("2018-04-08 12:30")
            .build();

    // Manually added - Goal's details found in {@code GoalCommandTestUtil}
    public static final Goal GOAL_A1 = new GoalBuilder().withCompletion(VALID_GOAL_COMPLETION_A)
            .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_A)
            .withGoalText(VALID_GOAL_TEXT_A).withImportance(VALID_GOAL_IMPORTANCE_A)
            .withStartDateTime(VALID_GOAL_START_DATE_TIME_STRING_A).build();
    public static final Goal GOAL_A2 = new GoalBuilder().withCompletion(VALID_GOAL_COMPLETION_B)
            .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_B)
            .withGoalText(VALID_GOAL_TEXT_B).withImportance(VALID_GOAL_IMPORTANCE_B)
            .withStartDateTime(VALID_GOAL_START_DATE_TIME_STRING_B).build();

    private TypicalGoals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical goals.
     */
    public static AddressBook getTypicalGoalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Goal goal : getTypicalGoals()) {
            try {
                ab.addGoal(goal);
            } catch (DuplicateGoalException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Goal> getTypicalGoals() {
        return new ArrayList<>(Arrays.asList(GOAL_A, GOAL_B, GOAL_C, GOAL_D, GOAL_E, GOAL_F, GOAL_G));
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see PersonCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "teal";

        case "colleagues":
        case "neighbours":
            return "yellow";

        case "family":
        case "friend":
            return "orange";

        case "friends":
            return "brown";

        case "husband":
            return "grey";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }


    /**
     * Asserts that the level of friendship in {@code actualCard} matches all the tags in {@code expectedPerson} with
     * the correct symbol.
     */
    private static void assertLevelOfFriendshipEqual(Person expectedPerson,
                                                PersonCardHandle actualCard) {
        String expectedLevelOfFriendship = expectedPerson.getLevelOfFriendship().value;
        int levelOfFriendshipInIntegerForm = Integer.parseInt((expectedLevelOfFriendship));
        String levelOfFriendshipSymbol = "";
        for (int i = 0; i < levelOfFriendshipInIntegerForm; i++) {
            levelOfFriendshipSymbol = levelOfFriendshipSymbol + '\u2665' + " ";
        }
        assertEquals(levelOfFriendshipSymbol, actualCard.getLevelOfFriendship());
    }

```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Changing @param ccaInArrayList into a CCA string in desired format
     * @return ccaInString
     */
    public static String getCcasInString(List<String> ccaInArrayList) {
        String ccaInString = "";
        for (String temp : ccaInArrayList) {
            temp = "[" + temp + "] ";
            ccaInString = ccaInString + temp;
        }
        return ccaInString.trim();
    }
}
```
