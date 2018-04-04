package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOAL;

import org.junit.Test;

import seedu.address.logic.commands.CompleteGoalCommand;
import seedu.address.logic.commands.CompleteGoalCommand.CompleteGoalDescriptor;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;

//@@author deborahlow97
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CompleteGoalCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CompleteGoalCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CompleteGoalCommandParserTest {

    private CompleteGoalCommandParser parser = new CompleteGoalCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteGoalCommand() {
        CompleteGoalDescriptor completeGoalDescriptor = new CompleteGoalDescriptor();
        completeGoalDescriptor.setCompletion(new Completion(true));
        completeGoalDescriptor.setEndDateTime(new EndDateTime("today"));
        assertParseSuccess(parser, "1", new CompleteGoalCommand(INDEX_FIRST_GOAL, completeGoalDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteGoalCommand.MESSAGE_USAGE));
    }
}
