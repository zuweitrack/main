package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_SORT_FIELD_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_SORT_FIELD_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_SORT_ORDER_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_SORT_ORDER_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_GOAL_SORT_FIELD;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_GOAL_SORT_ORDER;
import static seedu.address.logic.commands.GoalCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.GoalCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_SORT_FIELD_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_SORT_ORDER_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortGoalCommand;

//@@author deborahlow97
public class SortGoalCommandParserTest {
    private SortGoalCommandParser parser = new SortGoalCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GOAL_SORT_FIELD_DESC_B + GOAL_SORT_ORDER_DESC_B,
                new SortGoalCommand(VALID_GOAL_SORT_FIELD_B, VALID_GOAL_SORT_ORDER_B));

        // multiple goal sort fields - last sort goal field accepted
        assertParseSuccess(parser, GOAL_SORT_FIELD_DESC_A + GOAL_SORT_FIELD_DESC_B + GOAL_SORT_ORDER_DESC_B,
                new SortGoalCommand(VALID_GOAL_SORT_FIELD_B, VALID_GOAL_SORT_ORDER_B));

        // multiple goal sort order - last sort goal order accepted
        assertParseSuccess(parser, GOAL_SORT_FIELD_DESC_B + GOAL_SORT_ORDER_DESC_A + GOAL_SORT_ORDER_DESC_B,
                new SortGoalCommand(VALID_GOAL_SORT_FIELD_B, VALID_GOAL_SORT_ORDER_B));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE);

        // missing sort goal field prefix
        assertParseFailure(parser, GOAL_SORT_ORDER_DESC_B, expectedMessage);

        // missing sort goal order prefix
        assertParseFailure(parser, GOAL_SORT_FIELD_DESC_B, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE);
        // invalid sort goal field
        assertParseFailure(parser, INVALID_GOAL_SORT_FIELD + GOAL_SORT_ORDER_DESC_B,
                expectedMessage);

        // invalid sort goal order
        assertParseFailure(parser, INVALID_GOAL_SORT_ORDER + GOAL_SORT_FIELD_DESC_B,
                expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GOAL_SORT_ORDER_DESC_B + GOAL_SORT_FIELD_DESC_B,
                expectedMessage);
    }
}
