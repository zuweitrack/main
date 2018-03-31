package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_IMPORTANCE_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_IMPORTANCE_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_TEXT_DESC_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.GOAL_TEXT_DESC_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_GOAL_TEXT_DESC;
import static seedu.address.logic.commands.GoalCommandTestUtil.INVALID_IMPORTANCE_DESC;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_IMPORTANCE_B;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_A;
import static seedu.address.logic.commands.GoalCommandTestUtil.VALID_GOAL_TEXT_B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GOAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GOAL;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGoalCommand;
import seedu.address.logic.commands.EditGoalCommand.EditGoalDescriptor;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;
import seedu.address.testutil.EditGoalDescriptorBuilder;

//@@author deborahlow97
public class EditGoalCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGoalCommand.MESSAGE_USAGE);

    private EditGoalCommandParser parser = new EditGoalCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_GOAL_TEXT_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditGoalCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + GOAL_TEXT_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + GOAL_TEXT_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid importance
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC, Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // invalid importance followed by valid goal text
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC + GOAL_TEXT_DESC_B,
                Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // valid goal text followed by invalid importance.
        assertParseFailure(parser, "1" + GOAL_TEXT_DESC_B + INVALID_IMPORTANCE_DESC,
                Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);

        // invalid goal text
        assertParseFailure(parser, "1" + GOAL_IMPORTANCE_DESC_B + INVALID_GOAL_TEXT_DESC,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

        // valid importance followed by invalid goal text.
        assertParseFailure(parser, "1" + INVALID_GOAL_TEXT_DESC + GOAL_IMPORTANCE_DESC_A,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

        // invalid importance followed by invalid goal text. Last invalid value is captured
        assertParseFailure(parser, "1" + INVALID_IMPORTANCE_DESC + INVALID_GOAL_TEXT_DESC,
                GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A + GOAL_IMPORTANCE_DESC_B;

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A)
                .withImportance(VALID_GOAL_IMPORTANCE_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // goal text
        Index targetIndex = INDEX_THIRD_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A;
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_A).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // importance
        userInput = targetIndex.getOneBased() + GOAL_IMPORTANCE_DESC_A;
        descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_A).build();
        expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_GOAL;
        String userInput = targetIndex.getOneBased() + GOAL_TEXT_DESC_A + GOAL_IMPORTANCE_DESC_A
                + GOAL_TEXT_DESC_B + GOAL_IMPORTANCE_DESC_B;

        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withImportance(VALID_GOAL_IMPORTANCE_B)
                .withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    /*
    @Test
    public void parse_invalidValueFollowedByValidValue_fail() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_GOAL;
        String userInput = targetIndex.getOneBased() + INVALID_IMPORTANCE_DESC + GOAL_TEXT_DESC_B;
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder().withGoalText(VALID_GOAL_TEXT_B).build();
        EditGoalCommand expectedCommand = new EditGoalCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}
