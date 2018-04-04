package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

//@@author fuadsahmawi
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
