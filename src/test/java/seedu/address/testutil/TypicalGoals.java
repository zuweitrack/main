package seedu.address.testutil;

import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_START_DATE_TIME_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_START_DATE_TIME_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_B;
import static seedu.address.model.util.SampleGoalDataUtil.getLocalDateTimeFromString;

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
    public static final Goal BENSON = new GoalBuilder().withCompletion(true)
            .withEndDatTime("1 March 2018 10 30").withGoalText("eat fruits daily").withImportance("2")
            .withStartDateTime("#4-44").build();
    public static final Goal CARL = new GoalBuilder().withCompletion("Carl Kurz").withEndDatTime("95352563")
            .withGoalText("12-02-1994").withImportance("7").withStartDateTime("#2-69").build();
    public static final Goal DANIEL = new GoalBuilder().withCompletion("Daniel Meier").withEndDatTime("87652533")
            .withGoalText("12/03/1994").withImportance("4").withStartDateTime("#03-033").build();
    public static final Goal ELLE = new GoalBuilder().withCompletion("Elle Meyer").withEndDatTime("9482224")
            .withGoalText("09-09-1999").withImportance("10").withStartDateTime("#9-434")
            .withCcas("modern dance").build();
    public static final Goal FIONA = new GoalBuilder().withCompletion("Fiona Kunz").withEndDatTime("9482427")
            .withGoalText("10/10/1990").withImportance("3").withStartDateTime("#10-10").build();
    public static final Goal GEORGE = new GoalBuilder().withCompletion("George Best").withEndDatTime("9482442")
            .withGoalText("11/11/2000").withImportance("8").withStartDateTime("#2-65")
            .withMeetDate("14/04/2018").build();

    // Manually added
    public static final Goal HOON = new GoalBuilder().withCompletion("Hoon Meier").withEndDatTime("8482424")
            .withGoalText("15/05/1995").withImportance("1").withStartDateTime("#6-66")
            .build();
    public static final Goal IDA = new GoalBuilder().withCompletion("Ida Mueller").withEndDatTime("8482131")
            .withGoalText("14/04/1994").withImportance("3").withStartDateTime("#4-44")
            .build();
    public static final Goal JAKE = new GoalBuilder().withCompletion("Jake Black").withEndDatTime("8482131")
            .withGoalText("14/04/1995").withImportance("3").withStartDateTime("#4-45")
            .build();

    // Manually added - Goal's details found in {@code GoalCommandTestUtil}
    public static final Goal AMY = new GoalBuilder().withCompletion(VALID_NAME_AMY).withEndDatTime(VALID_PHONE_AMY)
            .withGoalText(VALID_BIRTHDAY_AMY).withImportance(VALID_LEVEL_OF_FRIENDSHIP_AMY)
            .withStartDateTime(VALID_UNIT_NUMBER_AMY).withCcas(VALID_CCA_DANCE).withTags(VALID_TAG_FRIEND).build();
    public static final Goal BOB = new GoalBuilder().withCompletion(VALID_NAME_BOB).withEndDatTime(VALID_PHONE_BOB)
            .withGoalText(VALID_BIRTHDAY_BOB).withImportance(VALID_LEVEL_OF_FRIENDSHIP_BOB)
            .withStartDateTime(VALID_UNIT_NUMBER_BOB).withCcas(VALID_CCA_DANCE, VALID_CCA_BADMINTON)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGoals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical goals.
     */
    public static AddressBook getTypicalAddressBook() {
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
        return new ArrayList<>(Arrays.asList(GOAL_A, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
