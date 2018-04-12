package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GOAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_BIRTHDAY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_LEVEL_OF_FRIENDSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SORT_MEET_DATE;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGoalCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CompleteGoalCommand;
import seedu.address.logic.commands.CompleteGoalCommand.CompleteGoalDescriptor;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGoalCommand;
import seedu.address.logic.commands.DeleteMeetCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditGoalCommand;
import seedu.address.logic.commands.EditGoalCommand.EditGoalDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MeetCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortGoalCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goal.Goal;
import seedu.address.model.person.Meet;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.CompleteGoalDescriptorBuilder;
import seedu.address.testutil.EditGoalDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GoalBuilder;
import seedu.address.testutil.GoalUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(
                AddCommand.COMMAND_ALIAS + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_meet() throws Exception {
        final String date = "14/10/2018";
        MeetCommand command  = (MeetCommand) parser.parseCommand(MeetCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_DATE + date);
        assertEquals(new MeetCommand(INDEX_FIRST_PERSON, new Meet(date)), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_ALIAS + " n/" + keywords.stream().collect(Collectors.joining(" ")) + " t/ ");
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_ALIAS + " n/" + keywords.stream().collect(Collectors.joining(" ")) + " t/ ");
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    //@@author deborahlow97
    @Test
    public void parseCommand_addGoal_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(GoalUtil.getAddGoalCommand(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_addGoalAliasOne_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(
                AddGoalCommand.COMMAND_ALIAS_1 + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_addGoalAliasTwo_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        AddGoalCommand command = (AddGoalCommand) parser.parseCommand(
                AddGoalCommand.COMMAND_ALIAS_2 + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new AddGoalCommand(goal), command);
    }

    @Test
    public void parseCommand_editGoal_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_editGoalAliasOne_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_ALIAS_1 + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_editGoalAliasTwo_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        EditGoalDescriptor descriptor = new EditGoalDescriptorBuilder(goal).build();
        EditGoalCommand command = (EditGoalCommand) parser.parseCommand(EditGoalCommand.COMMAND_ALIAS_2 + " "
                + INDEX_FIRST_GOAL.getOneBased() + " " + GoalUtil.getGoalDetails(goal));
        assertEquals(new EditGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_deleteGoal_returnsTrue() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_WORD + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_deleteGoalAliasOne_returnsTrue() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_ALIAS_1 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_deleteGoalAliasTwo_returnsTrue() throws Exception {
        DeleteGoalCommand command = (DeleteGoalCommand) parser.parseCommand(
                DeleteGoalCommand.COMMAND_ALIAS_2 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new DeleteGoalCommand(INDEX_FIRST_GOAL), command);
    }

    @Test
    public void parseCommand_completeGoal_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_WORD + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_completeGoalAliasOne_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_ALIAS_1 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_completeGoalAliasTwo_returnsTrue() throws Exception {
        Goal goal = new GoalBuilder().build();
        CompleteGoalDescriptor descriptor = new CompleteGoalDescriptorBuilder(goal).build();
        CompleteGoalCommand command = (CompleteGoalCommand) parser.parseCommand(
                CompleteGoalCommand.COMMAND_ALIAS_2 + " " + INDEX_FIRST_GOAL.getOneBased());
        assertEquals(new CompleteGoalCommand(INDEX_FIRST_GOAL, descriptor), command);
    }

    @Test
    public void parseCommand_theme_returnsTrue() throws Exception {
        ThemeCommand command = (ThemeCommand) parser.parseCommand(ThemeCommand.COMMAND_WORD + " " + "dark");
        assertEquals(new ThemeCommand("dark"), command);
    }

    @Test
    public void parseCommand_themeAlias_returnsTrue() throws Exception {
        ThemeCommand command = (ThemeCommand) parser.parseCommand(ThemeCommand.COMMAND_ALIAS + " " + "light");
        assertEquals(new ThemeCommand("light"), command);
    }

    @Test
    public void parseCommand_sortGoal_returnsTrue() throws Exception {
        SortGoalCommand command = (SortGoalCommand) parser.parseCommand(
                SortGoalCommand.COMMAND_WORD + " " + "f/importance" + " " + "o/ascending");
        assertEquals(new SortGoalCommand("importance", "ascending"), command);
    }

    @Test
    public void parseCommand_sortGoalAlias_returnsTrue() throws Exception {
        SortGoalCommand command = (SortGoalCommand) parser.parseCommand(
                SortGoalCommand.COMMAND_ALIAS + " " + "f/completion" + " " + "o/ascending");
        assertEquals(new SortGoalCommand("completion", "ascending"), command);

        command = (SortGoalCommand) parser.parseCommand(
                SortGoalCommand.COMMAND_ALIAS + " " + "f/startdatetime" + " " + "o/ascending");
        assertEquals(new SortGoalCommand("startdatetime", "ascending"), command);
    }

    @Test
    public void parseCommand_sortCommand_returnsTrue() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + 1);
        assertEquals(new SortCommand(INDEX_SORT_LEVEL_OF_FRIENDSHIP), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + 2);
        assertEquals(new SortCommand(INDEX_SORT_MEET_DATE), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + 3);
        assertEquals(new SortCommand(INDEX_SORT_BIRTHDAY), command);
    }

    @Test
    public void parseCommand_deleteMeetCommand_returnsTrue() throws Exception {
        DeleteMeetCommand command = (DeleteMeetCommand) parser.parseCommand(
                DeleteMeetCommand.COMMAND_WORD + " " + 1
        );
        assertEquals(new DeleteMeetCommand(INDEX_FIRST_PERSON), command);

        command = (DeleteMeetCommand) parser.parseCommand(
                DeleteMeetCommand.COMMAND_ALIAS + " " + 1
        );
        assertEquals(new DeleteMeetCommand(INDEX_FIRST_PERSON), command);
    }
}
