package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private TagContainsKeywordsPredicate predicateT = null;
    private NameContainsKeywordsPredicate predicateN = null;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicateN = predicate;
    }

    public FindCommand(TagContainsKeywordsPredicate predicate) {
        this.predicateT = predicate;
    }

    @Override
    public CommandResult execute() {
        if (predicateT == null)
        model.updateFilteredPersonList(predicateN);
        if (predicateN == null)
            model.updateFilteredPersonList(predicateT);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (this.predicateT == null) {
            return other == this // short circuit if same object
                    || (other instanceof FindCommand // instanceof handles nulls
                    && this.predicateN.equals(((FindCommand) other).predicateN)); // state check
        }
        else {
            return other == this // short circuit if same object
                    || (other instanceof FindCommand // instanceof handles nulls
                    && this.predicateT.equals(((FindCommand) other).predicateT)); // state check
        }
    }
}
