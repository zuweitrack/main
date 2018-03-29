package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code UnitNumber} matches any of the keywords given.
 */
public class UnitNumberContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public UnitNumberContainsKeywordsPredicate(List<String> keywords) {
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

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                | keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.toString(), keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnitNumberContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((UnitNumberContainsKeywordsPredicate) other).keywords)); // state check
    }

}
