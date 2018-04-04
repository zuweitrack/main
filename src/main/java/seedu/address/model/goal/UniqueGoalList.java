package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.goal.exceptions.GoalNotFoundException;

//@@author deborahlow97
/**
 * A list of goals that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Goal#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueGoalList implements Iterable<Goal> {

    private final ObservableList<Goal> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent goal as the given argument.
     */
    public boolean contains(Goal toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a goal to the list.
     *
     * @throws DuplicateGoalException if the goal to add is a duplicate of an existing goal in the list.
     */
    public void add(Goal toAdd) throws DuplicateGoalException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGoalException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the goal {@code target} in the list with {@code editedGoal}.
     *
     * @throws DuplicateGoalException if the replacement is equivalent to another existing goal in the list.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void setGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException {
        requireNonNull(editedGoal);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GoalNotFoundException();
        }

        if (!target.equals(editedGoal) && internalList.contains(editedGoal)) {
            throw new DuplicateGoalException();
        }

        internalList.set(index, editedGoal);
    }

    /**
     * Replaces the goal {@code target} in the list with {@code editedGoal}.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void setGoalWithoutParameters(Goal target, Goal editedGoal)
            throws GoalNotFoundException {
        requireNonNull(editedGoal);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GoalNotFoundException();
        }

        internalList.set(index, editedGoal);
    }

    /**
     * Removes the equivalent goal from the list.
     *
     * @throws GoalNotFoundException if no such goal could be found in the list.
     */
    public boolean remove(Goal toRemove) throws GoalNotFoundException {
        requireNonNull(toRemove);
        final boolean goalFoundAndDeleted = internalList.remove(toRemove);
        if (!goalFoundAndDeleted) {
            throw new GoalNotFoundException();
        }
        return goalFoundAndDeleted;
    }

    public void setGoals(UniqueGoalList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setGoals(List<Goal> goals) throws DuplicateGoalException {
        requireAllNonNull(goals);
        final UniqueGoalList replacement = new UniqueGoalList();
        for (final Goal goal : goals) {
            replacement.add(goal);
        }
        setGoals(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Goal> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Goal> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGoalList // instanceof handles nulls
                && this.internalList.equals(((UniqueGoalList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
