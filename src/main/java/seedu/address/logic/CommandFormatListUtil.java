
package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CompleteGoalCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGoalCommand;
import seedu.address.logic.commands.DeleteMeetCommand;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditGoalCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.commands.OngoingGoalCommand;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowLofCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortGoalCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author sham-sheer
/**
 * Initialises and returns a list which contains different command formats
 */
public final class CommandFormatListUtil {
    private static ArrayList<String> commandFormatList;

    public static ArrayList<String> getCommandFormatList () {
        commandFormatList = new ArrayList<>();
        createCommandFormatList();
        return commandFormatList;
    }

    /**
     * Creates commandFormatList for existing commands
     */
    private static void createCommandFormatList() {
        commandFormatList.add(AddCommand.COMMAND_FORMAT);
        commandFormatList.add(AddGoalCommand.COMMAND_FORMAT);
        commandFormatList.add(AddReminderCommand.COMMAND_WORD);
        commandFormatList.add(ClearCommand.COMMAND_WORD);
        commandFormatList.add(CompleteGoalCommand.COMMAND_WORD);
        commandFormatList.add(DeleteCommand.COMMAND_WORD);
        commandFormatList.add(DeleteGoalCommand.COMMAND_WORD);
        commandFormatList.add(DeleteMeetCommand.COMMAND_WORD);
        commandFormatList.add(DeleteReminderCommand.COMMAND_ALIAS_2);
        commandFormatList.add(EditCommand.COMMAND_FORMAT);
        commandFormatList.add(EditGoalCommand.COMMAND_WORD);
        commandFormatList.add(ExitCommand.COMMAND_WORD);
        commandFormatList.add(FindCommand.COMMAND_FORMAT);
        commandFormatList.add(HelpCommand.COMMAND_WORD);
        commandFormatList.add(HistoryCommand.COMMAND_WORD);
        commandFormatList.add(ListCommand.COMMAND_WORD);
        commandFormatList.add(MeetCommand.COMMAND_WORD);
        commandFormatList.add(OngoingGoalCommand.COMMAND_WORD);
        commandFormatList.add(RateCommand.COMMAND_WORD);
        commandFormatList.add(RedoCommand.COMMAND_WORD);
        commandFormatList.add(SelectCommand.COMMAND_WORD);
        commandFormatList.add(SortCommand.COMMAND_WORD);
        commandFormatList.add(SortGoalCommand.COMMAND_WORD);
        commandFormatList.add(ShowLofCommand.COMMAND_WORD);
        commandFormatList.add(ThemeCommand.COMMAND_WORD);
        commandFormatList.add(UndoCommand.COMMAND_WORD);

        //sorting the commandFormatList
        Collections.sort(commandFormatList);
    }
}