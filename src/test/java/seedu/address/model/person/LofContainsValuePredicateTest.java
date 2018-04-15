package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author zuweitrack
public class LofContainsValuePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LofContainsValuePredicate firstPredicate =
                new LofContainsValuePredicate(firstPredicateKeywordList);
        LofContainsValuePredicate secondPredicate =
                new LofContainsValuePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LofContainsValuePredicate firstPredicateCopy =
                new LofContainsValuePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lofContainsValues_returnsTrue() {
        // One keyword
        LofContainsValuePredicate predicate =
                new LofContainsValuePredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("1").build()));

        // Multiple key values
        predicate = new LofContainsValuePredicate(Arrays.asList("1", "3"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("1").build()));

        // Only one matching key value
        predicate = new LofContainsValuePredicate(Arrays.asList("3", "4"));
        assertTrue(predicate.test(new PersonBuilder().withLevelOfFriendship("4").build()));

    }

    @Test
    public void test_lofDoesNotContainKeyValues_returnsFalse() {
        // Zero keywords
        LofContainsValuePredicate predicate =
                new LofContainsValuePredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withLevelOfFriendship("2").build()));

        // Non-matching keyword
        predicate = new LofContainsValuePredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new PersonBuilder().withLevelOfFriendship("4").build()));

        // Keywords match phone, birthday, level of friendship, unit number,
        // but does not match level of friendship
        predicate = new LofContainsValuePredicate(Arrays.asList("96667444", "25/03/1997", "3", "#04-28"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("96667444")
                .withBirthday("25/03/1997").withLevelOfFriendship("4").withUnitNumber("#04-28").build()));
    }
}
