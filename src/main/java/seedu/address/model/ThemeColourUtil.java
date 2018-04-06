package seedu.address.model;

import java.util.HashMap;

/**
 * Contains utility methods for ThemeColour.
 */
public class ThemeColourUtil {

    private static final HashMap<String, String> themes;
    //@@author deborahlow97

    static {
        themes = new HashMap<>();
        themes.put("light", "view/LightTheme.css");
        themes.put("dark", "view/DarkTheme.css");
    }

    public static HashMap<String, String> getThemeHashMap() {
        return themes;
    }
}
