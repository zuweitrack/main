package seedu.address.logic.commands;

import seedu.address.model.person.UnitNumberContainsKeywordsPredicate;

/**
 * Finds and lists the Resident Assistant (RA) of an individual RC Student
 * in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class SeekRaCommand extends Command {

    public static final String COMMAND_WORD = "seek";

    public static final String COMMAND_ALIAS = "sk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Seeks the Resident Assistant (RA) of an individual RC student whose name contain any of "
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
