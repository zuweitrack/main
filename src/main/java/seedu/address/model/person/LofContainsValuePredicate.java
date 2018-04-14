package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author zuweitrack
/**
 * Tests that a {@code Person}'s {@code UnitNumber} matches any of the keywords given.
 */
public class LofContainsValuePredicate implements Predicate<Person> {
    private final List<String> keywords;

    public LofContainsValuePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                        (person.getLevelOfFriendship().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LofContainsValuePredicate // instanceof handles nulls
                && this.keywords.equals(((LofContainsValuePredicate) other).keywords)); // state check
    }

}
