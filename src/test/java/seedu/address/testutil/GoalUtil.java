package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;

import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.model.goal.Goal;

//@@author deborahlow97
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
