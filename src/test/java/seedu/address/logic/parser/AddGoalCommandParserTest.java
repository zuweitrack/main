package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_IMPORTANCE_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_IMPORTANCE_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_TEXT_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_TEXT_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_GOAL_TEXT_DESC;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_IMPORTANCE_DESC;
import static seedu.address.logic.commands.GoalCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.GoalCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_COMPLETION_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_END_DATE_TIME_STRING_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_START_DATE_TIME_STRING_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.testutil.GoalBuilder;

public class AddGoalCommandParserTest {
    private AddGoalCommandParser parser = new AddGoalCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Goal expectedGoal = new GoalBuilder().withCompletion(VALID_GOAL_COMPLETION_B).withGoalText(VALID_GOAL_TEXT_B)
                .withStartDateTime(VALID_GOAL_START_DATE_TIME_STRING_B).withImportance(VALID_GOAL_IMPORTANCE_B)
                .withEndDateTime(VALID_GOAL_END_DATE_TIME_STRING_B).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GOAL_IMPORTANCE_DESC_B + GOAL_TEXT_DESC_B,
                new AddGoalCommand(expectedGoal));

        // multiple goal importances - last goal importance accepted
        assertParseSuccess(parser, GOAL_IMPORTANCE_DESC_A + GOAL_IMPORTANCE_DESC_B + GOAL_TEXT_DESC_B,
                new AddGoalCommand(expectedGoal));

        // multiple goal texts - last goal text accepted
        assertParseSuccess(parser, GOAL_IMPORTANCE_DESC_B + GOAL_TEXT_DESC_A + GOAL_TEXT_DESC_B,
                new AddGoalCommand(expectedGoal));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoalCommand.MESSAGE_USAGE);

        // missing goal importance prefix
        assertParseFailure(parser, GOAL_TEXT_DESC_B, expectedMessage);

        // missing goal text prefix
        assertParseFailure(parser, GOAL_IMPORTANCE_DESC_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid goal importance
        assertParseFailure(parser, INVALID_IMPORTANCE_DESC + GOAL_TEXT_DESC_B,
                Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // invalid goal text
        assertParseFailure(parser, INVALID_GOAL_TEXT_DESC + GOAL_IMPORTANCE_DESC_B,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GOAL_TEXT_DESC_B + GOAL_IMPORTANCE_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoalCommand.MESSAGE_USAGE));
    }
}
