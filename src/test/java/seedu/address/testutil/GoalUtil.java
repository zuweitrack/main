package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.ui.StatusBarFooter.PERCENTAGE_KEY_NUMBER;

import javafx.collections.ObservableList;

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

    public static int getGoalCompletion(ObservableList<Goal> goalList) {
        int totalGoal = goalList.size();
        int totalGoalCompleted = 0;
        String completionStatus;
        for (int i = 0; i < totalGoal; i++) {
            completionStatus = goalList.get(i).getCompletion().value;
            totalGoalCompleted += isCompletedGoal(completionStatus);
        }
        int percentageGoalCompletion = (int) (((float) totalGoalCompleted / totalGoal) * PERCENTAGE_KEY_NUMBER);
        return percentageGoalCompletion;
    }

    /**
     * @param completionStatus gives a String that should be either "true" or "false", indicating if the goal is
     * completed.
     * @return true or false
     */
    private static int isCompletedGoal(String completionStatus) {
        int valueToAdd;
        if (completionStatus.equals("true")) {
            valueToAdd = 1;
        } else {
            valueToAdd = 0;
        }
        return valueToAdd;
    }
}
