package seedu.address.model.goal;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author deborahlow97
/**
 * Represents a Goal in the Goals Page.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Goal {

    private final Importance importance;
    private final GoalText goalText;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final Completion completion;

    /**
     * Every field must be present and not null.
     */

    public Goal(Importance importance, GoalText goalText, StartDateTime startDateTime,
                  EndDateTime endDateTime, Completion completion) {
        requireAllNonNull(importance, goalText, startDateTime, completion);
        this.importance = importance;
        this.goalText = goalText;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.completion = completion;
    }

    public Importance getImportance() {
        return importance;
    }

    public GoalText getGoalText() {
        return goalText;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    public Completion getCompletion() {
        return completion;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Goal)) {
            return false;
        }

        Goal otherPerson = (Goal) other;
        return otherPerson.getImportance().equals(this.getImportance())
                && otherPerson.getGoalText().equals(this.getGoalText());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(importance, goalText, startDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Importance: ")
                .append(getImportance())
                .append(" Goal Text: ")
                .append(getGoalText())
                .append(" Start Date Time: ")
                .append(getStartDateTime());

        return builder.toString();
    }

}
