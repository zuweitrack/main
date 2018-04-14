package seedu.address.logic.commands;

import seedu.address.model.person.LofContainsValuePredicate;

//@@author zuweitrack
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
