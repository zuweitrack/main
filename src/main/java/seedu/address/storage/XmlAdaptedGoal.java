package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;

//@@author deborahlow97
/**
 * JAXB-friendly version of the Goal.
 */
public class XmlAdaptedGoal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Goal's %s field is missing!";

    @XmlElement(required = true)
    private String importance;
    @XmlElement(required = true)
    private String goalText;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String completion;

    /**
     * Constructs an XmlAdaptedGoal.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGoal() {}

    /**
     * Constructs an {@code XmlAdaptedGoal} with the given goal details.
     */
    public XmlAdaptedGoal(String importance, String goalText, String startDateTime, String endDateTime,
                          String completion) {
        this.importance = importance;
        this.goalText = goalText;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.completion = completion;
    }

    /**
     * Converts a given Goal into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGoal
     */
    public XmlAdaptedGoal(Goal source) {
        importance = source.getImportance().value;
        goalText = source.getGoalText().value;
        startDateTime = source.getStartDateTime().value;
        endDateTime = source.getEndDateTime().value;
        completion = source.getCompletion().value;
    }

    /**
     * Converts this jaxb-friendly adapted goal object into the model's Goal object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted goal
     */
    public Goal toModelType() throws IllegalValueException {

        if (this.importance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Importance.class.getSimpleName()));
        }
        if (!Importance.isValidImportance(this.importance)) {
            throw new IllegalValueException(Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);
        }
        final Importance importance = new Importance(this.importance);

        if (this.goalText == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GoalText.class.getSimpleName()));
        }
        if (!GoalText.isValidGoalText(this.goalText)) {
            throw new IllegalValueException(GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);
        }
        final GoalText goalText = new GoalText(this.goalText);

        if (this.startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }

        final StartDateTime startDateTime = new StartDateTime(LocalDateTime.now());

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime
                    .class.getSimpleName()));
        }

        final EndDateTime endDateTime = new EndDateTime(this.endDateTime);

        if (this.completion == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Completion.class.getSimpleName()));
        }

        final Completion completion = new Completion((this.completion.equals("true")));
        return new Goal(importance, goalText, startDateTime, endDateTime, completion);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGoal)) {
            return false;
        }

        XmlAdaptedGoal otherGoal = (XmlAdaptedGoal) other;
        return Objects.equals(importance, otherGoal.importance)
                && Objects.equals(goalText, otherGoal.goalText)
                && Objects.equals(startDateTime, otherGoal.startDateTime)
                && Objects.equals(endDateTime, otherGoal.endDateTime)
                && Objects.equals(completion, otherGoal.completion);
    }
}

