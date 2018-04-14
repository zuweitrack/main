# zuweitrack
###### /java/seedu/address/logic/commands/RateCommand.java
``` java
/**
 * Rates existing person(s) in the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rates person(s) in College Zone "
            + "and changes the level of friendship "
            + "by the index number used in the latest listing.\n"
            + "Existing level of friendship will be overwritten by the input values.\n"
            + "Parameters: INDEX(s) (must be a positive integer) "
            + "[" + PREFIX_LEVEL_OF_FRIENDSHIP + "LEVELOFFRIENDSHIP] (between 1 and 10)\n"
            + "Example: " + COMMAND_WORD + " 1 3 "
            + PREFIX_LEVEL_OF_FRIENDSHIP + "5 ";

    private static final String MESSAGE_EDIT_PERSON_SUCCESS = "Rated person(s) successfully";
    private static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private static final String MESSAGE_PERSON_NOT_FOUND = "The selected person cannot be missing";

    private final List<Index> indexList;
    private final String levelOfFriendship;

    /**
     * @param indexList list of index(es) of the person in the filtered person list
     * @param levelOfFriendship new level of friendship to add to the person
     */
    public RateCommand(List<Index> indexList, String levelOfFriendship) {
        requireNonNull(indexList);
        requireNonNull(levelOfFriendship);

        this.indexList = indexList;
        this.levelOfFriendship = levelOfFriendship;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Person> latestList = model.getFilteredPersonList();
        for (Index index : indexList) {

            if (index.getZeroBased() >= latestList.size()) {
                throw new CommandException("One or more index inputs may not be valid"
                        + " and only the person(s) of valid indexes are being rated!");
            }

            Person selectedPerson = latestList.get(index.getZeroBased());

            try {
                Person editedPerson = new Person(selectedPerson.getName(), selectedPerson.getPhone(),
                        selectedPerson.getBirthday(), new LevelOfFriendship(levelOfFriendship),
                        selectedPerson.getUnitNumber(),
                        selectedPerson.getCcas(), selectedPerson.getMeetDate(), selectedPerson.getTags());
                model.updatePerson(selectedPerson, editedPerson);

            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);

            }

        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);

    }

}
```
###### /java/seedu/address/logic/commands/SeekRaCommand.java
``` java
/**
 * Finds and lists the Resident Assistant (RA) of an individual RC Student
 * in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class SeekRaCommand extends Command {

    public static final String COMMAND_WORD = "seek";

    public static final String COMMAND_ALIAS = "sk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Seeks and lists all Resident Assistants (RA) of RC4 with the"
            + " individual RC student whose name contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final UnitNumberContainsKeywordsPredicate predicate;

    public SeekRaCommand(UnitNumberContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForRaShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SeekRaCommand // instanceof handles nulls
                && this.predicate.equals(((SeekRaCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ShowLofCommand.java
``` java
/**
 * Finds and lists the person(s)
 * in address book whose level of friendship matches the input value
 * of the argument keywords.
 */
public class ShowLofCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows person(s) in CollegeZone with the"
            + " whose level of friendship contains any of "
            + "specified level and displays them as a list with index numbers.\n"
            + "Parameters: LEVELOFFRIENDSHIP [MORE_LEVELOFFRIENDSHIP]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 7";

    private final LofContainsValuePredicate predicate;

    public ShowLofCommand(LofContainsValuePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForRaShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowLofCommand // instanceof handles nulls
                && this.predicate.equals(((ShowLofCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/RateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser {

    /**
     * Returns true if the level of friendship prefix "/*" is present
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(groupPrefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if the level of friendship is between 1 - 10
     */
    private static boolean containsValidRange(String levelOfFriendship) {
        return levelOfFriendship.matches("0?[1-9]|[1][0]");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse (String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LEVEL_OF_FRIENDSHIP);

        if (!isPrefixesPresent(argumentMultimap, PREFIX_LEVEL_OF_FRIENDSHIP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }
        String levelOfFriendship = argumentMultimap.getValue(PREFIX_LEVEL_OF_FRIENDSHIP).get();

        if (!containsValidRange(levelOfFriendship)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        String preamble;
        String[] indexString;
        List<Index> indexList = new ArrayList<>();

        try {
            preamble = argumentMultimap.getPreamble();
            indexString = preamble.split("\\s+");
            for (String index : indexString) {
                indexList.add(ParserUtil.parseIndex(index));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RateCommand.MESSAGE_USAGE));
        }

        return new RateCommand(indexList, new String(levelOfFriendship));
    }

}
```
###### /java/seedu/address/logic/parser/SeekRaCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SeekRaCommand object
 */
public class SeekRaCommandParser implements Parser<SeekRaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SeekRaCommand
     * and returns an SeekRaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SeekRaCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeekRaCommand.MESSAGE_USAGE));
        }

        trimmedArgs = trimmedArgs + " " + "RA";

        String[] nameKeywords = (trimmedArgs.split("\\s+"));

        return new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/ShowLofCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShowLofCommand object
 */
public class ShowLofCommandParser implements Parser<ShowLofCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowLofCommand
     * and returns an ShowLofCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowLofCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLofCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = (trimmedArgs.split("\\s+"));

        return new ShowLofCommand(new LofContainsValuePredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/model/person/LofContainsValuePredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code UnitNumber} matches any of the keywords given.
 */
public class LofContainsValuePredicate implements Predicate<Person> {
    private final List<String> keywords;

    public LofContainsValuePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                        (person.getLevelOfFriendship().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LofContainsValuePredicate // instanceof handles nulls
                && this.keywords.equals(((LofContainsValuePredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/person/UnitNumberContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code UnitNumber} matches any of the keywords given.
 */
public class UnitNumberContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public UnitNumberContainsKeywordsPredicate(List<String> keywords) {
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

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                | keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnitNumberContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((UnitNumberContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
