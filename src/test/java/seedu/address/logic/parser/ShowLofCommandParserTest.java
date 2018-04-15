package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ShowLofCommand;
import seedu.address.model.person.LofContainsValuePredicate;

//@@author zuweitrack
public class ShowLofCommandParserTest {

    private ShowLofCommandParser parser = new ShowLofCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowLofCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsShowLofCommand() {
        // no leading and trailing whitespaces
        ShowLofCommand expectedShowLofCommand =
                new ShowLofCommand(new LofContainsValuePredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, " 1 2", expectedShowLofCommand);

        // multiple whitespaces between index values
        assertParseSuccess(parser, " 1  2 ", expectedShowLofCommand);
    }

}
