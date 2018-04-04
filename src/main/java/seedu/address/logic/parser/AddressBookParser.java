package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteGoalCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGoalCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditGoalCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SeekRaCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
@SuppressWarnings("CheckStyle")
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case SeekRaCommand.COMMAND_WORD:
            return new SeekRaCommandParser().parse(arguments);

        case SeekRaCommand.COMMAND_ALIAS:
            return new SeekRaCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case MeetCommand.COMMAND_WORD:
            return new MeetCommandParser().parse(arguments);

        case AddGoalCommand.COMMAND_WORD:
        case AddGoalCommand.COMMAND_ALIAS_1:
        case AddGoalCommand.COMMAND_ALIAS_2:
            return new AddGoalCommandParser().parse(arguments);

        case EditGoalCommand.COMMAND_WORD:
        case EditGoalCommand.COMMAND_ALIAS_1:
        case EditGoalCommand.COMMAND_ALIAS_2:
            return new EditGoalCommandParser().parse(arguments);

        case DeleteGoalCommand.COMMAND_WORD:
        case DeleteGoalCommand.COMMAND_ALIAS_1:
        case DeleteGoalCommand.COMMAND_ALIAS_2:
            return new DeleteGoalCommandParser().parse(arguments);

        case CompleteGoalCommand.COMMAND_WORD:
        case CompleteGoalCommand.COMMAND_ALIAS_1:
        case CompleteGoalCommand.COMMAND_ALIAS_2:
            return new CompleteGoalCommandParser().parse(arguments);

        case AddReminderCommand.COMMAND_WORD:
        case AddReminderCommand.COMMAND_ALIAS:
        case AddReminderCommand.COMMAND_ALIAS_2:
            return new AddReminderCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

}
