package seedu.address.testutil;

import seedu.address.logic.commands.CompleteGoalCommand.CompleteGoalDescriptor;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;

//@@author deborahlow97
/**
 * A utility class to help with building CompleteGoalDescriptor objects.
 */
public class CompleteGoalDescriptorBuilder {

    private CompleteGoalDescriptor descriptor;

    public CompleteGoalDescriptorBuilder() {
        descriptor = new CompleteGoalDescriptor();
    }

    public CompleteGoalDescriptorBuilder(CompleteGoalDescriptor descriptor) {
        this.descriptor = new CompleteGoalDescriptor(descriptor);
    }

    /**
     * Returns an {@code CompleteGoalDescriptor} with fields containing {@code goal}'s details
     */
    public CompleteGoalDescriptorBuilder(Goal goal) {
        descriptor = new CompleteGoalDescriptor();
        descriptor.setCompletion(new Completion(true));
        descriptor.setEndDateTime(new EndDateTime("today"));
        //TODO
        //descriptor.setCompletion(goal.getCompletion());
        //descriptor.setEndDateTime(goal.getEndDateTime());
    }

    /**
     * Sets the {@code Completion} of the {@code CompleteGoalDescriptor} that we are building.
     */
    public CompleteGoalDescriptorBuilder withCompletion(boolean isCompleted) {
        descriptor.setCompletion(new Completion(isCompleted));
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code CompleteGoalDescriptor} that we are building.
     */
    public CompleteGoalDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setEndDateTime(new EndDateTime(endDateTime));
        return this;
    }

    public CompleteGoalDescriptor build() {
        return descriptor;
    }
}
