package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author fuadsahmawi
/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */

public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Iterator<Tag> ir = person.getTags().iterator();
        StringBuilder tag = new StringBuilder();
        while (ir.hasNext()) {
            tag.append(ir.next().tagName);
            tag.append(" ");
        }

        String tagS = tag.toString();

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagS, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
