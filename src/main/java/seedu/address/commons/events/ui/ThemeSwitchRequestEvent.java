package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author deborahlow97
/**
 * Indicates that a theme switch is requested.
 */
public class ThemeSwitchRequestEvent extends BaseEvent {
    public final String themeToChangeTo;

    public ThemeSwitchRequestEvent(String themeToChangeTo) {
        this.themeToChangeTo = themeToChangeTo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
