package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

//@@author deborahlow97
/**
 * A list of ccas that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Cca#equals(Object)
 */
public class UniqueCcaList implements Iterable<Cca> {

    private final ObservableList<Cca> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CcaList.
     */
    public UniqueCcaList() {}

    /**
     * Creates a UniqueCcaList using given ccas.
     * Enforces no nulls.
     */
    public UniqueCcaList(Set<Cca> ccas) {
        requireAllNonNull(ccas);
        internalList.addAll(ccas);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all ccas in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Cca> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Ccas in this list with those in the argument cca list.
     */
    public void setCcas(Set<Cca> ccas) {
        requireAllNonNull(ccas);
        internalList.setAll(ccas);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCcaList from) {
        final Set<Cca> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(cca -> !alreadyInside.contains(cca))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Cca as the given argument.
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Cca to the list.
     *
     * @throws DuplicateCcaException if the Cca to add is a duplicate of an existing Cca in the list.
     */
    public void add(Cca toAdd) throws DuplicateCcaException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCcaException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Cca> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueCcaList // instanceof handles nulls
                && this.internalList.equals(((UniqueCcaList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueCcaList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCcaException extends DuplicateDataException {
        protected DuplicateCcaException() {
            super("Operation would result in duplicate ccas");
        }
    }

}
