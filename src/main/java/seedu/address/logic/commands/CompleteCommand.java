package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GOALS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.goal.Birthday;
import seedu.address.model.goal.Cca;
import seedu.address.model.goal.LevelOfFriendship;
import seedu.address.model.goal.Meet;
import seedu.address.model.goal.Name;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.goal.exceptions.GoalNotFoundException;

//@@author deborahlow97
/**
 * Edits the details of an existing goal in the address book.
 */
public class CompleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "!goal";
    public static final String COMMAND_ALIAS_1 = "!g";
    public static final String COMMAND_ALIAS_2 = "completedgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate completion of the goal identified "
            + "by the index number used in the last goal listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_COMPLETE_GOAL_SUCCESS = "Completed Goal: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This goal already exists in the address book.";

    private final Index index;
    private final EditGoalDescriptor editGoalDescriptor;

    private Goal goalToEdit;
    private Goal editedGoal;

    /**
     * @param index of the goal in the filtered goal list to edit
     * @param editGoalDescriptor details to edit the goal with
     */
    public CompleteCommand(Index index, EditGoalDescriptor editGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(editGoalDescriptor);

        this.index = index;
        this.editGoalDescriptor = new EditGoalDescriptor(editGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoal(goalToEdit, editedGoal);
        } catch (DuplicateGoalException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        goalToEdit = lastShownList.get(index.getZeroBased());
        editedGoal = createEditedGoal(goalToEdit, editGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToEdit}
     * edited with {@code editGoalDescriptor}.
     */
    private static Goal createEditedGoal(Goal goalToEdit, EditGoalDescriptor editGoalDescriptor) {
        assert goalToEdit != null;

        Name updatedName = editGoalDescriptor.getName().orElse(goalToEdit.getName());
        Phone updatedPhone = editGoalDescriptor.getPhone().orElse(goalToEdit.getPhone());
        Birthday updatedBirthday = editGoalDescriptor.getBirthday().orElse(goalToEdit.getBirthday());
        LevelOfFriendship updatedLevelOfFriendship = editGoalDescriptor.getLevelOfFriendship()
                .orElse(goalToEdit.getLevelOfFriendship());
        UnitNumber updatedUnitNumber = editGoalDescriptor.getUnitNumber().orElse(goalToEdit.getUnitNumber());
        Meet updatedMeetDate = goalToEdit.getMeetDate();
        Set<Cca> updatedCcas = editGoalDescriptor.getCcas().orElse(goalToEdit.getCcas());
        Set<Tag> updatedTags = editGoalDescriptor.getTags().orElse(goalToEdit.getTags());

        return new Goal(updatedName, updatedPhone, updatedBirthday, updatedLevelOfFriendship, updatedUnitNumber,
                updatedCcas, updatedMeetDate, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompleteCommand)) {
            return false;
        }

        // state check
        CompleteCommand e = (CompleteCommand) other;
        return index.equals(e.index)
                && editGoalDescriptor.equals(e.editGoalDescriptor)
                && Objects.equals(goalToEdit, e.goalToEdit);
    }

    /**
     * Stores the details to edit the goal with. Each non-empty field value will replace the
     * corresponding field value of the goal.
     */
    public static class EditGoalDescriptor {
        private Name name;
        private Phone phone;
        private Birthday birthday;
        private LevelOfFriendship levelOfFriendship;
        private UnitNumber unitNumber;
        private Set<Cca> ccas;
        private Set<Tag> tags;

        public EditGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGoalDescriptor(EditGoalDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setBirthday(toCopy.birthday);
            setLevelOfFriendship(toCopy.levelOfFriendship);
            setUnitNumber(toCopy.unitNumber);
            setCcas(toCopy.ccas);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.birthday,
                    this.levelOfFriendship, this.unitNumber, this.ccas, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

        public void setLevelOfFriendship(LevelOfFriendship levelOfFriendship) {
            this.levelOfFriendship = levelOfFriendship;
        }

        public Optional<LevelOfFriendship> getLevelOfFriendship() {
            return Optional.ofNullable(levelOfFriendship);
        }

        public void setUnitNumber(UnitNumber unitNumber) {
            this.unitNumber = unitNumber;
        }

        public Optional<UnitNumber> getUnitNumber() {
            return Optional.ofNullable(unitNumber);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGoalDescriptor)) {
                return false;
            }

            // state check
            EditGoalDescriptor e = (EditGoalDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getBirthday().equals(e.getBirthday())
                    && getLevelOfFriendship().equals(e.getLevelOfFriendship())
                    && getUnitNumber().equals(e.getUnitNumber())
                    && getCcas().equals(e.getCcas())
                    && getTags().equals(e.getTags());
        }
    }
}
