package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ReminderBuilder;

//@@author fuadsahamwi
public class ReminderTextPredicateTest {

    @Test
    public void equals() {

        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ReminderTextPredicate firstPredicate = new ReminderTextPredicate(firstPredicateKeywordList);
        ReminderTextPredicate secondPredicate = new ReminderTextPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ReminderTextPredicate firstPredicateCopy = new ReminderTextPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different reminder -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_reminderTextContainsKeywords_returnsTrue() {
        // One keyword
        ReminderTextPredicate predicate = new ReminderTextPredicate(Collections.singletonList("gym"));
        assertTrue(predicate.test(new ReminderBuilder().withReminderText("Go gym").build()));

        // Multiple keywords
        predicate = new ReminderTextPredicate(Arrays.asList("Go", "gym"));
        assertTrue(predicate.test(new ReminderBuilder().withReminderText("Go gym").build()));

        // Only one matching keyword
        predicate = new ReminderTextPredicate(Arrays.asList("Star", "Gym"));
        assertTrue(predicate.test(new ReminderBuilder().withReminderText("Start Gym").build()));

        // Mixed-case keywords
        predicate = new ReminderTextPredicate(Arrays.asList("gO", "gYM"));
        assertTrue(predicate.test(new ReminderBuilder().withReminderText("Go gym").build()));
    }

    @Test
    public void test_reminderTextDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ReminderTextPredicate predicate = new ReminderTextPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ReminderBuilder().withReminderText("Gym").build()));

        // Non-matching keyword
        predicate = new ReminderTextPredicate(Arrays.asList("Finals"));
        assertFalse(predicate.test(new ReminderBuilder().withReminderText("Midterms").build()));

        // Keywords match start date time and end date time, but does not match reminder text
        predicate = new ReminderTextPredicate(Arrays.asList("tmr", "8pm", "tmr", "10pm"));
        assertFalse(predicate.test(new ReminderBuilder().withReminderText("Alice").withDateTime("tmr 8pm")
                .withEndDateTime("tmr 10pm").build()));
    }
}
