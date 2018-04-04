package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author fuadsahmawi
/**
 * Tests that a {@code Reminder}'s {@code ReminderText} matches any of the keywords given.
 */
public class ReminderTextPredicate implements Predicate<Reminder> {
    private final List<String> keywords;

    public ReminderTextPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Reminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getReminderText().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderTextPredicate // instanceof handles nulls
                && this.keywords.equals(((ReminderTextPredicate) other).keywords)); // state check
    }
}
