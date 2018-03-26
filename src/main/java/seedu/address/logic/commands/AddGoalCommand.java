package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEXT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;

public class AddGoalCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "+goal";
    public static final String COMMAND_ALIAS = "+g";
}
