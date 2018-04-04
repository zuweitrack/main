package seedu.address.model.util;

import static seedu.address.logic.parser.DateTimeParser.getLocalDateTimeFromString;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.model.goal.StartDateTime;
import seedu.address.model.goal.exceptions.DuplicateGoalException;

//@@author deborahlow97
/**
 * Contains utility methods for populating {@code CollegeZone} with sample data.
 */
public class SampleGoalDataUtil {

    public static final EndDateTime EMPTY_END_DATE_TIME = new EndDateTime("");

    public static Goal[] getSampleGoals() {
        return new Goal[] {

            new Goal(new Importance("1"), new GoalText("finish cs2103"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("2"), new GoalText("no"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-08 12:12")),
                    EMPTY_END_DATE_TIME, new Completion(true)),
            new Goal(new Importance("3"), new GoalText("grow taller"),
                    new StartDateTime(getLocalDateTimeFromString("1997-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("3"), new GoalText("finish cs2105 assignments"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-08 10:30")),
                    EMPTY_END_DATE_TIME, new Completion(true)),
            new Goal(new Importance("1"), new GoalText("learning digital art"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:39")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("2"), new GoalText("finish cs2103!!!!"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Goal sampleGoal : getSampleGoals()) {
                sampleAb.addGoal(sampleGoal);
            }
            return sampleAb;
        } catch (DuplicateGoalException e) {
            throw new AssertionError("sample data cannot contain duplicate goals", e);
        }
    }
}
