package seedu.address.testutil;

import seedu.address.logic.commands.EditGoalCommand.EditGoalDescriptor;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.GoalText;
import seedu.address.model.goal.Importance;

//@@author deborahlow97
/**
 * A utility class to help with building EditGoalDescriptor objects.
 */
public class EditGoalDescriptorBuilder {

    private EditGoalDescriptor descriptor;

    public EditGoalDescriptorBuilder() {
        descriptor = new EditGoalDescriptor();
    }

    public EditGoalDescriptorBuilder(EditGoalDescriptor descriptor) {
        this.descriptor = new EditGoalDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGoalDescriptor} with fields containing {@code goal}'s details
     */
    public EditGoalDescriptorBuilder(Goal goal) {
        descriptor = new EditGoalDescriptor();
        descriptor.setGoalText(goal.getGoalText());
        descriptor.setImportance(goal.getImportance());
    }

    /**
     * Sets the {@code GoalText} of the {@code EditGoalDescriptor} that we are building.
     */
    public EditGoalDescriptorBuilder withGoalText(String goalText) {
        descriptor.setGoalText(new GoalText(goalText));
        return this;
    }

    /**
     * Sets the {@code Importance} of the {@code EditGoalDescriptor} that we are building.
     */
    public EditGoalDescriptorBuilder withImportance(String importance) {
        descriptor.setImportance(new Importance(importance));
        return this;
    }

    public EditGoalDescriptor build() {
        return descriptor;
    }
}

