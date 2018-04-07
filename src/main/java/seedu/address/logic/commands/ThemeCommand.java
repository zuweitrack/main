package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ThemeSwitchRequestEvent;

//@@author deborahlow97
/**
 * Changes the CollegeZone colour theme to either dark or light.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_SUCCESS = "Theme successfully changed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme to the theme word entered.\n"
            + "Parameters: COLOUR THEME\n"
            + "(Colour theme words: dark, light)\n"
            + "Example: " + COMMAND_WORD + " dark\n";
    public static final String MESSAGE_INVALID_THEME_COLOUR = "Theme colour entered is invalid.\n"
            + "Possible theme colours:\n"
            + "(Colour theme words: dark, light)\n";
    public static final String MESSAGE_ALREADY_IN_CURRENT_THEME = "CollegeZone is already in the theme colour.";
    private final String themeColour;

    /**
     * Creates a ThemeCommand based on the specified themeColour.
     */
    public ThemeCommand (String themeColour) {
        requireNonNull(themeColour);
        this.themeColour = themeColour;
    }

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new ThemeSwitchRequestEvent(themeColour));
        return new CommandResult(String.format(MESSAGE_SUCCESS, themeColour));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && themeColour.equals(((ThemeCommand) other).themeColour));
    }
}
