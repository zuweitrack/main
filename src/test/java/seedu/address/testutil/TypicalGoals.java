package seedu.address.testutil;

import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_START_DATE_TIME_STRING_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_START_DATE_TIME_STRING_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;

//@@author deborahlow97
/**
 * A utility class containing a list of {@code Goal} objects to be used in tests.
 */
public class TypicalGoals {

    public static final Goal GOAL_A = new GoalBuilder().withCompletion(false)
            .withEndDateTime("").withGoalText("stay fit").withImportance("1")
            .withStartDateTime("2017-04-08 12:30").build();
    public static final Goal GOAL_B = new GoalBuilder().withCompletion(false)
            .withEndDateTime("").withGoalText("eat fruits daily").withImportance("2")
            .withStartDateTime("2017-05-08 12:30").build();
    public static final Goal GOAL_C = new GoalBuilder().withCompletion(true).withEndDateTime("2018-04-08 12:30")
            .withGoalText("aa").withImportance("7").withStartDateTime("2017-06-08 12:30").build();
    public static final Goal GOAL_D = new GoalBuilder().withCompletion(true).withEndDateTime("2018-04-08 12:31")
            .withGoalText("bb").withImportance("4").withStartDateTime("2017-06-08 12:31").build();
    public static final Goal GOAL_E = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("cc").withImportance("10").withStartDateTime("2017-06-08 12:32")
            .build();
    public static final Goal GOAL_F = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("dd").withImportance("3").withStartDateTime("2017-06-08 12:33").build();
    public static final Goal GOAL_G = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("ee").withImportance("8").withStartDateTime("2017-06-08 12:35")
            .build();

    // Manually added
    public static final Goal HOON = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("ff").withImportance("1").withStartDateTime("2017-06-08 12:36")
            .build();
    public static final Goal IDA = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("gg").withImportance("3").withStartDateTime("2017-06-08 12:38")
            .build();
    public static final Goal JAKE = new GoalBuilder().withCompletion(false).withEndDateTime("")
            .withGoalText("hii").withImportance("3").withStartDateTime("2018-04-08 12:30")
            .build();

    // Manually added - Goal's details found in {@code GoalCommandTestUtil}
    public static final Goal GOAL_A1 = new GoalBuilder().withCompletion(VALID_GOAL_COMPLETION_A)
            .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_A)
            .withGoalText(VALID_GOAL_TEXT_A).withImportance(VALID_GOAL_IMPORTANCE_A)
            .withStartDateTime(VALID_GOAL_START_DATE_TIME_STRING_A).build();
    public static final Goal GOAL_A2 = new GoalBuilder().withCompletion(VALID_GOAL_COMPLETION_B)
            .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_B)
            .withGoalText(VALID_GOAL_TEXT_B).withImportance(VALID_GOAL_IMPORTANCE_B)
            .withStartDateTime(VALID_GOAL_START_DATE_TIME_STRING_B).build();

    private TypicalGoals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical goals.
     */
    public static AddressBook getTypicalGoalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Goal goal : getTypicalGoals()) {
            try {
                ab.addGoal(goal);
            } catch (DuplicateGoalException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Goal> getTypicalGoals() {
        return new ArrayList<>(Arrays.asList(GOAL_A, GOAL_B, GOAL_C, GOAL_D, GOAL_E, GOAL_F, GOAL_G));
    }
}
