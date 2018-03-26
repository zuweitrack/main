package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;

public interface GoalModel {


    /** Adds the given goal */
    void addGoal(Goal goal) throws DuplicateGoalException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Goal> getFilteredGoalList();
}
