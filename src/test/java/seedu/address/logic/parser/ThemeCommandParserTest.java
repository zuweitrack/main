package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

//@@author deborahlow97

public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        assertParseSuccess(parser, "light", new ThemeCommand("light"));
    }

    @Test
    public void parser_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ThemeCommand.MESSAGE_INVALID_THEME_COLOUR));

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ThemeCommand.MESSAGE_USAGE));
    }
}
