package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SeekRaCommand;
import seedu.address.model.person.UnitNumberContainsKeywordsPredicate;

public class SeekRaCommandParserTest {

    private SeekRaCommandParser parser = new SeekRaCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeekRaCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSeekRaCommand() {
        // no leading and trailing whitespaces
        SeekRaCommand expectedSeekRaCommand =
                new SeekRaCommand(new UnitNumberContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "RA")));
        assertParseSuccess(parser, " Alice Bob", expectedSeekRaCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " Alice  Bob ", expectedSeekRaCommand);
    }

}
