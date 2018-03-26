package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author deborahlow97
/**
 * Represents a Person's Level of Friendship in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevelOfFriendship(String)}
 */
public class LevelOfFriendship {

    public static final String MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS =
            "Level of Friendship should only be a numerical integer value between 1 to 10";
    public static final String LEVEL_OF_FRIENDSHIP_VALIDATION_REGEX = "[0-9]+";
    private static final int MINIMUM_LEVEL_OF_FRIENDSHIP = 1;
    private static final int MAXIMUM_LEVEL_OF_FRIENDSHIP = 10;
    private static int levelOfFriendshipInIntegerForm;
    public final String value;

    /**
     * * Constructs an {@code LevelOfFriendship}.
     *
     * @param levelOfFriendship A valid level of friendship number.
     */
    public LevelOfFriendship(String levelOfFriendship) {
        requireNonNull(levelOfFriendship);
        checkArgument(isValidLevelOfFriendship(levelOfFriendship), MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        this.value = levelOfFriendship;
    }

    /**
     * Returns true if a given string is a valid person level of friendship.
     */
    public static boolean isValidLevelOfFriendship(String test) {

        return test.matches(LEVEL_OF_FRIENDSHIP_VALIDATION_REGEX) && isAnIntegerWithinRange(test);
    }

    /**
     * Returns true if a given string is an integer and within range of level of friendship.
     */
    private static boolean isAnIntegerWithinRange(String test) {
        levelOfFriendshipInIntegerForm = Integer.parseInt(test);
        if (levelOfFriendshipInIntegerForm >= MINIMUM_LEVEL_OF_FRIENDSHIP
                && levelOfFriendshipInIntegerForm <= MAXIMUM_LEVEL_OF_FRIENDSHIP) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LevelOfFriendship // instanceof handles nulls
                && this.value.equals(((LevelOfFriendship) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
