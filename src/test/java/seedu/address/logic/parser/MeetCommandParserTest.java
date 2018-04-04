package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.model.person.Meet;

public class MeetCommandParserTest {
    private MeetCommandParser parser = new MeetCommandParser();
    private final String nonEmptyDate = "15/03/2018";

    @Test
    public void parse_indexSpecified_success() throws Exception {
        //have a date
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DATE.toString() + nonEmptyDate;
        MeetCommand expectedCommand = new MeetCommand(INDEX_FIRST_PERSON, new Meet(nonEmptyDate));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, MeetCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, MeetCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }

}
