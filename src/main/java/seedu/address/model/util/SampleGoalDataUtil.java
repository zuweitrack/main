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
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("3"), new GoalText("grow taller"),
                    new StartDateTime(getLocalDateTimeFromString("1997-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("3"), new GoalText("finish cs2105 assignments"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-08 10:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("1"), new GoalText("learning digital art"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:39")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("2"), new GoalText("finish cs2103!!!!"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("10"), new GoalText("finish cs2103!!!!"),
                    new StartDateTime(getLocalDateTimeFromString("2018-03-18 08:30")),
                    new EndDateTime("03/04/2018 12:30"), new Completion(true)),
            new Goal(new Importance("6"), new GoalText("lose 0.5kg by this week"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-06 19:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("10"), new GoalText("Find love <3"),
                    new StartDateTime(getLocalDateTimeFromString("2014-04-08 20:30")),
                    new EndDateTime("02/02/2018 12:30"), new Completion(true)),
            new Goal(new Importance("7"), new GoalText("water plants"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("2"), new GoalText("buy dog food"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("4"), new GoalText("Take the stairs more often!"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("04/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("10"), new GoalText("Eat PGP MALA once every week"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("07/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("1"), new GoalText("Make more friends in uni"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:45")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("2"), new GoalText("Go CCA regularly"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 13:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("9"), new GoalText("Drink 8 cups of water everyday"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 01:59")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("8"), new GoalText("Get A for CS2105"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 02:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("8"), new GoalText("Get A- for GEH1036"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 03:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("10"), new GoalText("Aim to increase CAP by 0.2 by the end of this semester"),
                    new StartDateTime(getLocalDateTimeFromString("2017-02-18 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("10"), new GoalText("Do 50 squats EVERYDAY"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
        };
    }

    public static ReadOnlyAddressBook getSampleGoalAddressBook() {
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
