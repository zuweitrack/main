package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author deborahlow97
/**
 * Represents a Goal's text in the Goals Page.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoalText(String)}
 */
public class GoalText {


    public static final String MESSAGE_GOAL_TEXT_CONSTRAINTS =
            "Goal text can be any expression that are not just whitespaces.";
    public static final String GOAL_TEXT_VALIDATION_REGEX = "^(?!\\s*$).+";
    public final String value;

    /**
     * Constructs a {@code GoalText}.
     *
     * @param goalText A valid goal text.
     */
    public GoalText(String goalText) {
        requireNonNull(goalText);
        checkArgument(isValidGoalText(goalText), MESSAGE_GOAL_TEXT_CONSTRAINTS);
        this.value = goalText;
    }

    /**
     * Returns true if a given string is a valid goal text.
     */
    public static boolean isValidGoalText(String test) {
        return test.matches(GOAL_TEXT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalText // instanceof handles nulls
                && this.value.equals(((GoalText) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
