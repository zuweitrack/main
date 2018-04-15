# zuweitrack
###### \java\seedu\address\logic\commands\SeekRaCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SeekRaCommand}.
 */
public class SeekRaCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        UnitNumberContainsKeywordsPredicate firstPredicate =
                new UnitNumberContainsKeywordsPredicate(Collections.singletonList("first"));
        UnitNumberContainsKeywordsPredicate secondPredicate =
                new UnitNumberContainsKeywordsPredicate(Collections.singletonList("second"));

        SeekRaCommand seekRaFirstCommand = new SeekRaCommand(firstPredicate);
        SeekRaCommand seekRaSecondCommand = new SeekRaCommand(secondPredicate);

        // same object -> returns true
        assertTrue(seekRaFirstCommand.equals(seekRaFirstCommand));

        // same values -> returns true
        SeekRaCommand seekRaFirstCommandCopy = new SeekRaCommand(firstPredicate);
        assertTrue(seekRaFirstCommand.equals(seekRaFirstCommandCopy));

        // different types -> returns false
        assertFalse(seekRaFirstCommand.equals(1));

        // null -> returns false
        assertFalse(seekRaFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(seekRaFirstCommand.equals(seekRaSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 0);
        SeekRaCommand command = prepareNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 3);
        SeekRaCommand command = prepareNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_RA_LISTED_OVERVIEW, 7);
        SeekRaCommand command = prepareTagCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code SeekRaCommand}.
     */
    private SeekRaCommand prepareNameCommand(String userInput) {
        SeekRaCommand command =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code SeekRaCommand}.
     */
    private SeekRaCommand prepareTagCommand(String userInput) {
        SeekRaCommand command =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SeekRaCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ShowLofCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ShowLofCommand}.
 */
public class ShowLofCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroValues_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ShowLofCommand command = prepareLofCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeyValues_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ShowLofCommand command = prepareLofCommand("7 10 3");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }


    /**
     * Parses {@code userInput} into a {@code ShowLofCommand}.
     */
    private ShowLofCommand prepareLofCommand(String userInput) {
        ShowLofCommand command =
                new ShowLofCommand(new LofContainsValuePredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ShowLofCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\SeekRaCommandParserTest.java
``` java
public class SeekRaCommandParserTest {

    private SeekRaCommandParser parser = new SeekRaCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeekRaCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSeekRaCommand() {
        // no leading and trailing whitespaces
        SeekRaCommand expectedSeekRaCommand =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "RA")));
        assertParseSuccess(parser, " Alice Bob", expectedSeekRaCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " Alice  Bob ", expectedSeekRaCommand);
    }

}
```
###### \java\seedu\address\logic\parser\ShowLofCommandParserTest.java
``` java
public class ShowLofCommandParserTest {

    private ShowLofCommandParser parser = new ShowLofCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLofCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsShowLofCommand() {
        // no leading and trailing whitespaces
        ShowLofCommand expectedShowLofCommand =
                new ShowLofCommand(new LofContainsValuePredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " 1 2", expectedShowLofCommand);

        // multiple whitespaces between index values
        assertParseSuccess(parser, " 1  2 ", expectedShowLofCommand);
    }

}
```
###### \java\seedu\address\model\person\LofContainsValuePredicateTest.java
``` java
public class LofContainsValuePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LofContainsValuePredicate firstPredicate =
                new LofContainsValuePredicate(firstPredicateKeywordList);
        LofContainsValuePredicate secondPredicate =
                new LofContainsValuePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LofContainsValuePredicate firstPredicateCopy =
                new LofContainsValuePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lofContainsValues_returnsTrue() {
        // One keyword
        LofContainsValuePredicate predicate =
                new LofContainsValuePredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("1").build()));

        // Multiple key values
        predicate = new LofContainsValuePredicate(Arrays.asList("1", "3"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("1").build()));

        // Only one matching key value
        predicate = new LofContainsValuePredicate(Arrays.asList("3", "4"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("4").build()));

    }

    @Test
    public void test_lofDoesNotContainKeyValues_returnsFalse() {
        // Zero keywords
        LofContainsValuePredicate predicate =
                new LofContainsValuePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withLevelOfFriendship("2").build()));

        // Non-matching keyword
        predicate = new LofContainsValuePredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new PersonBuilder().withLevelOfFriendship("4").build()));

        // Keywords match phone, birthday, level of friendship, unit number,
        // but does not match level of friendship
        predicate = new LofContainsValuePredicate(Arrays.asList("96667444", "25/03/1997", "3", "#04-28"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("96667444")
                .withBirthday("25/03/1997").withLevelOfFriendship("4").withUnitNumber("#04-28").build()));
    }
}
```
###### \java\seedu\address\model\person\UnitNumberContainsKeywordsPredicateTest.java
``` java
public class UnitNumberContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        UnitNumberContainsKeywordsPredicate firstPredicate =
                new UnitNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        UnitNumberContainsKeywordsPredicate secondPredicate =
                new UnitNumberContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UnitNumberContainsKeywordsPredicate firstPredicateCopy =
                new UnitNumberContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        UnitNumberContainsKeywordsPredicate predicate =
                new UnitNumberContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new UnitNumberContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new UnitNumberContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new UnitNumberContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        UnitNumberContainsKeywordsPredicate predicate =
                new UnitNumberContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new UnitNumberContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, birthday, level of friendship, unit number, but does not match name
        predicate = new UnitNumberContainsKeywordsPredicate(Arrays.asList("92474733", "23/06/1996", "3", "#4-49"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92474733")
                .withBirthday("23/06/1996").withLevelOfFriendship("3").withUnitNumber("#4-49").build()));
    }
}
```
