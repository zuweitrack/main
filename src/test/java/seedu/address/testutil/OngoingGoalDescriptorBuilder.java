package seedu.address.testutil;

import seedu.address.logic.commands.OngoingGoalCommand.OngoingGoalDescriptor;
import seedu.address.model.goal.Completion;
import seedu.address.model.goal.EndDateTime;
import seedu.address.model.goal.Goal;

//@@author deborahlow97
/**
 * A utility class to help with building OngoingGoalDescriptor objects.
 */
public class OngoingGoalDescriptorBuilder {

    private OngoingGoalDescriptor descriptor;

    public OngoingGoalDescriptorBuilder() {
        descriptor = new OngoingGoalDescriptor();
    }

    public OngoingGoalDescriptorBuilder(OngoingGoalDescriptor descriptor) {
        this.descriptor = new OngoingGoalDescriptor(descriptor);
    }

    /**
     * Returns an {@code OngoingGoalDescriptor} with fields containing {@code goal}'s details
     */
    public OngoingGoalDescriptorBuilder(Goal goal) {
        descriptor = new OngoingGoalDescriptor();
        descriptor.setCompletion(new Completion(false));
        descriptor.setEndDateTime(new EndDateTime(""));
    }

    /**
     * Sets the {@code Completion} of the {@code OngoingGoalDescriptor} that we are building.
     */
    public OngoingGoalDescriptorBuilder withCompletion(boolean isOngoing) {
        descriptor.setCompletion(new Completion(isOngoing));
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code OngoingGoalDescriptor} that we are building.
     */
    public OngoingGoalDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setEndDateTime(new EndDateTime(endDateTime));
        return this;
    }

    public OngoingGoalDescriptor build() {
        return descriptor;
    }
}
