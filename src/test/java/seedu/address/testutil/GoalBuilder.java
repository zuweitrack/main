package seedu.address.testutil;

import static seedu.address.logic.parser.DateTimeParser.getLocalDateTimeFromString;

import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;

//@@author deborahlow97
/**
 * A utility class to help with building Person objects.
 */
public class GoalBuilder {

    public static final boolean DEFAULT_COMPLETION = false;
    public static final String DEFAULT_END_DATE_TIME = "";
    public static final String DEFAULT_GOAL_TEXT = "er yea acadamic no la no la";
    public static final String DEFAULT_IMPORTANCE = "1";
    public static final String DEFAULT_START_DATE_TIME = "2017-04-08 12:30";

    private Completion completion;
    private EndDateTime endDateTime;
    private GoalText goalText;
    private Importance importance;
    private StartDateTime startDateTime;

    public GoalBuilder() {
        completion = new Completion(DEFAULT_COMPLETION);
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
        goalText = new GoalText(DEFAULT_GOAL_TEXT);
        importance = new Importance(DEFAULT_IMPORTANCE);
        startDateTime = new StartDateTime(getLocalDateTimeFromString(DEFAULT_START_DATE_TIME));
    }

    /**
     * Initializes the GoalBuilder with the data of {@code goalToCopy}.
     */
    public GoalBuilder(Goal goalToCopy) {
        completion = goalToCopy.getCompletion();
        endDateTime = goalToCopy.getEndDateTime();
        goalText = goalToCopy.getGoalText();
        importance = goalToCopy.getImportance();
        startDateTime = goalToCopy.getStartDateTime();
    }

    /**
     * Sets the {@code Completion} of the {@code Goal} that we are building.
     */
    public GoalBuilder withCompletion(Boolean completion) {
        this.completion = new Completion(completion);
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Goal} that we are building.
     */
    public GoalBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code GoalText} of the {@code Goal} that we are building.
     */
    public GoalBuilder withGoalText(String goalText) {
        this.goalText = new GoalText(goalText);
        return this;
    }

    /**
     * Sets the {@code Importance} of the {@code Goal} that we are building.
     */
    public GoalBuilder withImportance(String importance) {
        this.importance = new Importance(importance);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Goal} that we are building.
     */
    public GoalBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new StartDateTime(getLocalDateTimeFromString(startDateTime));
        return this;
    }

    public Goal build() {
        return new Goal(importance, goalText, startDateTime, endDateTime, completion);
    }

}

