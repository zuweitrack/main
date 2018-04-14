# deborahlow97
###### /java/seedu/address/commons/events/ui/ThemeSwitchRequestEvent.java
``` java
/**
 * Indicates that a theme switch is requested.
 */
public class ThemeSwitchRequestEvent extends BaseEvent {
    public final String themeToChangeTo;

    public ThemeSwitchRequestEvent(String themeToChangeTo) {
        this.themeToChangeTo = themeToChangeTo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/AddGoalCommand.java
``` java
/**
 * Adds a goal to CollegeZone.
 */
public class AddGoalCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "+goal";
    public static final String COMMAND_ALIAS_1 = "+g";
    public static final String COMMAND_ALIAS_2 = "addgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a goal to Goals Page. \n"
            + "Parameters: "
            + PREFIX_IMPORTANCE + "IMPORTANCE "
            + PREFIX_GOAL_TEXT + "TEXT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IMPORTANCE + "3 "
            + PREFIX_GOAL_TEXT + "lose weight \n";

    public static final String MESSAGE_SUCCESS = "New goal added: %1$s";
    public static final String MESSAGE_DUPLICATE_GOAL = "This goal already exists in the Goals Page";

    private final Goal toAdd;

    /**
     * Creates an AddGoalCommand to add the specified {@code Goal}
     */
    public AddGoalCommand(Goal goal) {
        requireNonNull(goal);
        toAdd = goal;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addGoal(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateGoalException e) {
            throw new CommandException(MESSAGE_DUPLICATE_GOAL);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGoalCommand // instanceof handles nulls
                && toAdd.equals(((AddGoalCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/CompleteGoalCommand.java
``` java
/**
 * Edits the details of an existing goal in CollegeZone.
 */
public class CompleteGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "!goal";
    public static final String COMMAND_ALIAS_1 = "!g";
    public static final String COMMAND_ALIAS_2 = "completegoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate completion of the goal identified "
            + "by the index number used in the last goal listing.\n "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_COMPLETE_GOAL_SUCCESS = "Completed Goal! : %1$s";

    private final Index index;
    private final CompleteGoalDescriptor completeGoalDescriptor;

    private Goal goalToUpdate;
    private Goal updatedGoal;

    /**
     * @param index of the goal in the filtered goal list to update
     */
    public CompleteGoalCommand(Index index, CompleteGoalDescriptor completeGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(completeGoalDescriptor);

        this.index = index;
        this.completeGoalDescriptor = new CompleteGoalDescriptor(completeGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoalWithoutParameters(goalToUpdate, updatedGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_COMPLETE_GOAL_SUCCESS, updatedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToUpdate = lastShownList.get(index.getZeroBased());
        if (goalToUpdate.getCompletion().hasCompleted) {
            throw new CommandException(Messages.MESSAGE_GOAL_COMPLETED_ERROR);
        }
        updatedGoal = createUpdatedGoal(goalToUpdate, completeGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToUpdate}
     * edited with {@code completeGoalDescriptor}.
     */
    private static Goal createUpdatedGoal(Goal goalToUpdate, CompleteGoalDescriptor completeGoalDescriptor) {
        assert goalToUpdate != null;

        GoalText goalText = completeGoalDescriptor.getGoalText().orElse(goalToUpdate.getGoalText());
        Importance importance = completeGoalDescriptor.getImportance().orElse(goalToUpdate.getImportance());
        StartDateTime startDateTime = completeGoalDescriptor.getStartDateTime().orElse(goalToUpdate.getStartDateTime());
        EndDateTime updatedEndDateTime = completeGoalDescriptor.getEndDateTime()
                .orElse(goalToUpdate.getEndDateTime());
        Completion updatedCompletion = completeGoalDescriptor.getCompletion().orElse(goalToUpdate.getCompletion());

        return new Goal(importance, goalText, startDateTime, updatedEndDateTime, updatedCompletion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompleteGoalCommand)) {
            return false;
        }

        // state check
        CompleteGoalCommand e = (CompleteGoalCommand) other;
        return index.equals(e.index)
                && completeGoalDescriptor.equals(e.completeGoalDescriptor)
                && Objects.equals(goalToUpdate, e.goalToUpdate);
    }

    /**
     * Stores the details to update the goal with.
     */
    public static class CompleteGoalDescriptor {
        private GoalText goalText;
        private Importance importance;
        private StartDateTime startDateTime;
        private EndDateTime endDateTime;
        private Completion completion;

        public CompleteGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public CompleteGoalDescriptor(CompleteGoalDescriptor toCopy) {
            setEndDateTime(toCopy.endDateTime);
            setCompletion(toCopy.completion);
        }

        public void setEndDateTime(EndDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setCompletion(Completion completion) {
            this.completion = completion;
        }

        public Optional<Completion> getCompletion() {
            return Optional.ofNullable(completion);
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public Optional<Importance> getImportance() {
            return Optional.ofNullable(importance);
        }
        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(goalText);
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof CompleteGoalDescriptor)) {
                return false;
            }

            // state check
            CompleteGoalDescriptor e = (CompleteGoalDescriptor) other;

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getCompletion().equals(e.getCompletion());
        }
    }
}
```
###### /java/seedu/address/logic/commands/DeleteGoalCommand.java
``` java
/**
 * Deletes a goal identified using it's last displayed index from CollegeZone.
 */
public class DeleteGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "-goal";

    public static final String COMMAND_ALIAS_1 = "-g";

    public static final String COMMAND_ALIAS_2 = "deletegoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the goal identified by the index number used in the goal listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GOAL_SUCCESS = "Deleted Goal: %1$s";

    private final Index targetIndex;

    private Goal goalToDelete;

    public DeleteGoalCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(goalToDelete);
        try {
            model.deleteGoal(goalToDelete);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_GOAL_SUCCESS, goalToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoalCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteGoalCommand) other).targetIndex) // state check
                && Objects.equals(this.goalToDelete, ((DeleteGoalCommand) other).goalToDelete));
    }
}
```
###### /java/seedu/address/logic/commands/EditGoalCommand.java
``` java
/**
 * Edits the details of an existing goal in CollegeZone.
 */
public class EditGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "~goal";
    public static final String COMMAND_ALIAS_1 = "~g";
    public static final String COMMAND_ALIAS_2 = "editgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the goal identified "
            + "by the index number used in the last goal listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GOAL_TEXT + "GOAL TEXT] "
            + "[" + PREFIX_IMPORTANCE + "IMPORTANCE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_IMPORTANCE + "2 ";

    public static final String MESSAGE_EDIT_GOAL_SUCCESS = "Edited Goal: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GOAL = "This goal already exists in the address book.";

    private final Index index;
    private final EditGoalDescriptor editGoalDescriptor;

    private Goal goalToEdit;
    private Goal editedGoal;

    /**
     * @param index of the goal in the filtered goal list to edit
     * @param editGoalDescriptor details to edit the goal with
     */
    public EditGoalCommand(Index index, EditGoalDescriptor editGoalDescriptor) {
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
            throw new CommandException(MESSAGE_DUPLICATE_GOAL);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_EDIT_GOAL_SUCCESS, editedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
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

        GoalText updatedGoalText = editGoalDescriptor.getGoalText().orElse(goalToEdit.getGoalText());
        Importance updatedImportance = editGoalDescriptor.getImportance().orElse(goalToEdit.getImportance());
        StartDateTime startDateTime = goalToEdit.getStartDateTime();
        EndDateTime endDateTime = goalToEdit.getEndDateTime();
        Completion completion = goalToEdit.getCompletion();
        return new Goal(updatedImportance, updatedGoalText, startDateTime, endDateTime, completion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGoalCommand)) {
            return false;
        }

        // state check
        EditGoalCommand e = (EditGoalCommand) other;
        return index.equals(e.index)
                && editGoalDescriptor.equals(e.editGoalDescriptor)
                && Objects.equals(goalToEdit, e.goalToEdit);
    }

    /**
     * Stores the details to edit the goal with. Each non-empty field value will replace the
     * corresponding field value of the goal.
     */
    public static class EditGoalDescriptor {
        private GoalText goalText;
        private Importance importance;

        public EditGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGoalDescriptor(EditGoalDescriptor toCopy) {
            setGoalText(toCopy.goalText);
            setImportance(toCopy.importance);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.goalText, this.importance);
        }

        public void setGoalText(GoalText goalText) {
            this.goalText = goalText;
        }

        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(goalText);
        }

        public void setImportance(Importance importance) {
            this.importance = importance;
        }

        public Optional<Importance> getImportance() {
            return Optional.ofNullable(importance);
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

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance());
        }
    }
}

```
###### /java/seedu/address/logic/commands/OngoingGoalCommand.java
``` java
/**
 * Edits the details of an existing goal in CollegeZone.
 */
public class OngoingGoalCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "-!goal";
    public static final String COMMAND_ALIAS_1 = "-!g";
    public static final String COMMAND_ALIAS_2 = "ongoinggoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate identified goal is not completed "
            + "and still ongoing.\n"
            + "Goal is identified "
            + "by the index number used in the last goal listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ONGOING_GOAL_SUCCESS = "Ongoing Goal! : %1$s";

    private final Index index;
    private final OngoingGoalDescriptor ongoingGoalDescriptor;

    private Goal goalToUpdate;
    private Goal updatedGoal;

    /**
     * @param index of the goal in the filtered goal list to update
     */
    public OngoingGoalCommand(Index index, OngoingGoalDescriptor ongoingGoalDescriptor) {
        requireNonNull(index);
        requireNonNull(ongoingGoalDescriptor);

        this.index = index;
        this.ongoingGoalDescriptor = new OngoingGoalDescriptor(ongoingGoalDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateGoalWithoutParameters(goalToUpdate, updatedGoal);
        } catch (GoalNotFoundException pnfe) {
            throw new AssertionError("The target goal cannot be missing");
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_ONGOING_GOAL_SUCCESS, updatedGoal));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Goal> lastShownList = model.getFilteredGoalList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOAL_DISPLAYED_INDEX);
        }

        goalToUpdate = lastShownList.get(index.getZeroBased());
        if (!goalToUpdate.getCompletion().hasCompleted) {
            throw new CommandException(Messages.MESSAGE_GOAL_ONGOING_ERROR);
        }
        updatedGoal = createUpdatedGoal(goalToUpdate, ongoingGoalDescriptor);
    }

    /**
     * Creates and returns a {@code Goal} with the details of {@code goalToUpdate}
     * edited with {@code ongoingGoalDescriptor}.
     */
    private static Goal createUpdatedGoal(Goal goalToUpdate, OngoingGoalDescriptor ongoingGoalDescriptor) {
        assert goalToUpdate != null;

        GoalText goalText = ongoingGoalDescriptor.getGoalText().orElse(goalToUpdate.getGoalText());
        Importance importance = ongoingGoalDescriptor.getImportance().orElse(goalToUpdate.getImportance());
        StartDateTime startDateTime = ongoingGoalDescriptor.getStartDateTime().orElse(goalToUpdate.getStartDateTime());
        EndDateTime updatedEndDateTime = ongoingGoalDescriptor.getEndDateTime()
                .orElse(goalToUpdate.getEndDateTime());
        Completion updatedCompletion = ongoingGoalDescriptor.getCompletion().orElse(goalToUpdate.getCompletion());

        return new Goal(importance, goalText, startDateTime, updatedEndDateTime, updatedCompletion);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OngoingGoalCommand)) {
            return false;
        }

        // state check
        OngoingGoalCommand e = (OngoingGoalCommand) other;
        return index.equals(e.index)
                && ongoingGoalDescriptor.equals(e.ongoingGoalDescriptor)
                && Objects.equals(goalToUpdate, e.goalToUpdate);
    }

    /**
     * Stores the details to update the goal with.
     */
    public static class OngoingGoalDescriptor {
        private GoalText goalText;
        private Importance importance;
        private StartDateTime startDateTime;
        private EndDateTime endDateTime;
        private Completion completion;

        public OngoingGoalDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public OngoingGoalDescriptor(OngoingGoalDescriptor toCopy) {
            setEndDateTime(toCopy.endDateTime);
            setCompletion(toCopy.completion);
        }

        public void setEndDateTime(EndDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setCompletion(Completion completion) {
            this.completion = completion;
        }

        public Optional<Completion> getCompletion() {
            return Optional.ofNullable(completion);
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public Optional<Importance> getImportance() {
            return Optional.ofNullable(importance);
        }
        public Optional<GoalText> getGoalText() {
            return Optional.ofNullable(goalText);
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof OngoingGoalDescriptor)) {
                return false;
            }

            // state check
            OngoingGoalDescriptor e = (OngoingGoalDescriptor) other;

            return getGoalText().equals(e.getGoalText())
                    && getImportance().equals(e.getImportance())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getCompletion().equals(e.getCompletion());
        }
    }
}
```
###### /java/seedu/address/logic/commands/SortGoalCommand.java
``` java
/**
 * Sorts goal list in CollegeZone based on sort field entered by user.
 */
public class SortGoalCommand extends Command {

    public static final String COMMAND_WORD = "sortgoal";
    public static final String COMMAND_ALIAS = "sgoal";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts CollegeZone's goals based on the field entered.\n"
            + "Parameters: "
            + PREFIX_SORT_FIELD + "FIELD (must be 'importance', 'startdatetime' or 'completion') "
            + PREFIX_SORT_ORDER + "ORDER (must be either 'ascending' or 'descending')\n"
            + "Example: " + COMMAND_WORD + " f/completion o/ascending";

    public static final String MESSAGE_SUCCESS = "Sorted all goals by %s and %s";
    private String sortField;
    private String sortOrder;

    public SortGoalCommand(String field, String order) {
        this.sortField = field;
        this.sortOrder = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.sortGoal(sortField, sortOrder);
        } catch (EmptyGoalListException egle) {
            throw new CommandException(Messages.MESSAGE_INVALID_SORT_COMMAND_USAGE);
        }
        model.updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortField, sortOrder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortGoalCommand // instanceof handles nulls
                && sortField.equals(((SortGoalCommand) other).sortField));
    }
}
```
###### /java/seedu/address/logic/commands/ThemeCommand.java
``` java
/**
 * Changes the CollegeZone colour theme to either dark, bubblegum or light.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "th";
    public static final String MESSAGE_SUCCESS = "Theme successfully changed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme to the theme word entered.\n"
            + "Parameters: COLOUR THEME\n"
            + "(Colour theme words: dark, bubblegum, light)\n"
            + "Example: " + COMMAND_WORD + " dark\n";
    public static final String MESSAGE_INVALID_THEME_COLOUR = "Theme colour entered is invalid.\n"
            + "Possible theme colours:\n"
            + "(Colour theme words: dark, bubblegum, light)\n";
    private final String themeColour;

    /**
     * Creates a ThemeCommand based on the specified themeColour.
     */
    public ThemeCommand (String themeColour) {
        requireNonNull(themeColour);
        this.themeColour = themeColour;
    }

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new ThemeSwitchRequestEvent(themeColour));
        return new CommandResult(String.format(MESSAGE_SUCCESS, themeColour));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && themeColour.equals(((ThemeCommand) other).themeColour));
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY,
                        PREFIX_LEVEL_OF_FRIENDSHIP, PREFIX_UNIT_NUMBER, PREFIX_CCA, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_BIRTHDAY, PREFIX_PHONE, PREFIX_UNIT_NUMBER,
                PREFIX_LEVEL_OF_FRIENDSHIP, PREFIX_UNIT_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            UnitNumber unitNumber = ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNIT_NUMBER)).get();
            LevelOfFriendship levelOfFriendship = ParserUtil.parseLevelOfFriendship(argMultimap
                    .getValue(PREFIX_LEVEL_OF_FRIENDSHIP)).get();
            Set<Cca> ccaList = ParserUtil.parseCcas(argMultimap.getAllValues(PREFIX_CCA));
            Meet meetDate = new Meet("");
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Person person = new Person(name, phone, birthday, levelOfFriendship, unitNumber, ccaList, meetDate,
                    tagList);
            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

```
###### /java/seedu/address/logic/parser/AddGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddGoalCommand object
 */
public class AddGoalCommandParser implements Parser<AddGoalCommand> {

    public static final String EMPTY_END_DATE_TIME = "";
    public static final boolean INITIAL_COMPLETION_STATUS = false;
    /**
     * Parses the given {@code String} of arguments in the context of the AddGoalCommand
     * and returns an AddGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IMPORTANCE, PREFIX_GOAL_TEXT);

        if (!arePrefixesPresent(argMultimap, PREFIX_IMPORTANCE, PREFIX_GOAL_TEXT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoalCommand.MESSAGE_USAGE));
        }

        try {
            Importance importance = ParserUtil.parseImportance(argMultimap.getValue(PREFIX_IMPORTANCE)).get();
            GoalText goalText = ParserUtil.parseGoalText(argMultimap.getValue(PREFIX_GOAL_TEXT)).get();
            StartDateTime startDateTime = new StartDateTime(LocalDateTime.now());
            EndDateTime endDateTime = new EndDateTime(EMPTY_END_DATE_TIME);
            Completion completion = new Completion(INITIAL_COMPLETION_STATUS);
            Goal goal = new Goal(importance, goalText, startDateTime, endDateTime, completion);
            return new AddGoalCommand(goal);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/parser/CompleteGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CompleteGoalCommand object
 */
public class CompleteGoalCommandParser implements Parser<CompleteGoalCommand> {

    public static final boolean COMPLETED_BOOLEAN_VALUE = true;

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteGoalCommand
     * and returns an CompleteGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteGoalCommand parse(String args) throws ParseException {

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteGoalCommand.MESSAGE_USAGE));
        }

        CompleteGoalDescriptor completeGoalDescriptor = new CompleteGoalDescriptor();

        Optional<String> empty = Optional.empty();
        Completion completion = new Completion(COMPLETED_BOOLEAN_VALUE);
        EndDateTime endDateTime = new EndDateTime(properDateTimeFormat(LocalDateTime.now()));
        completeGoalDescriptor.setCompletion(completion);
        completeGoalDescriptor.setEndDateTime(endDateTime);


        return new CompleteGoalCommand(index, completeGoalDescriptor);
    }
}
```
###### /java/seedu/address/logic/parser/DateTimeParser.java
``` java
/**
 * Contains utility methods used for parsing DateTime in the various *Parser classes.
 */
public class DateTimeParser {

    private static final int BEGIN_INDEX = 6;
    /**
     * Parses user input String specified{@code args} into LocalDateTime objects
     *
     * @return Empty Optional if args could not be parsed
     * @Disclaimer : The parser used is a NLP API called 'natty' developed by 'Joe Stelmach'
     */
    public static Optional<LocalDateTime> nattyDateAndTimeParser(String args) {
        if (args == null || args.isEmpty()) {
            return Optional.empty();
        }

        Parser parser = new Parser();
        List groups = parser.parse(args);

        //Cannot be parsed
        if (groups.size() <= 0) {
            return Optional.empty();
        }

        DateGroup dateGroup = (DateGroup) groups.get(0);
        if (dateGroup.getDates().size() < 0) {
            return Optional.empty();
        }

        Date date = dateGroup.getDates().get(0);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Receives two LocalDateTime and ensures that the specified {@code endDateTime} is always later in time than
     * specified {@code startDateTime}
     *
     * @return endDateTime that checks the above confirmation
     */
    public static LocalDateTime balanceStartAndEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime newEndDateTime = endDateTime;
        while (startDateTime.compareTo(newEndDateTime) >= 1) {
            newEndDateTime = newEndDateTime.plusDays(1);
        }
        return newEndDateTime;
    }
    /**
     * Receives a LocalDateTime and formats the {@code dateTime}
     *
     * @return a formatted dateTime in String
     */
    public static String properDateTimeFormat(LocalDateTime dateTime) {
        StringBuilder builder = new StringBuilder();
        int day = dateTime.getDayOfMonth();
        String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        builder.append("Date: ")
                .append(day)
                .append(" ")
                .append(month)
                .append(" ")
                .append(year)
                .append(",  Time: ")
                .append(String.format("%02d", hour))
                .append(":")
                .append(String.format("%02d", minute));
        return builder.toString();
    }

    public static LocalDateTime getLocalDateTimeFromProperDateTime(String properDateTimeString) {
        String trimmedArgs = properDateTimeString.trim();
        int size = trimmedArgs.length();
        String stringFormat = properDateTimeString.substring(BEGIN_INDEX, size);
        stringFormat = stringFormat.replace(", Time: ", "");
        return nattyDateAndTimeParser(stringFormat).get();
    }

    /**
     * Receives a LocalDateTime and formats the {@code dateTime}
     *
     * @return a formatted dateTime in String
     */
    public static String properReminderDateTimeFormat(LocalDateTime dateTime) {
        StringBuilder builder = new StringBuilder();
        int day = dateTime.getDayOfMonth();
        String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        builder.append(day)
                .append("/")
                .append(month)
                .append("/")
                .append(year)
                .append(" ")
                .append(String.format("%02d", hour))
                .append(":")
                .append(String.format("%02d", minute));
        return builder.toString();
    }

    public static boolean containsDateAndTime(String args) {
        return nattyDateAndTimeParser(args).isPresent();
    }

    public static LocalDateTime getLocalDateTimeFromString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime;
    }
}
```
###### /java/seedu/address/logic/parser/DeleteGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteGoalCommand object
 */
public class DeleteGoalCommandParser implements Parser<DeleteGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoalCommand
     * and returns an DeleteGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGoalCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteGoalCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoalCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> ccas} into a {@code Set<Cca>} if {@code ccas} is non-empty.
     * If {@code ccas} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Cca>} containing zero ccas.
     */
    private Optional<Set<Cca>> parseCcasForEdit(Collection<String> ccas) throws IllegalValueException {
        assert ccas != null;

        if (ccas.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> ccaSet = ccas.size() == 1 && ccas.contains("") ? Collections.emptySet() : ccas;
        return Optional.of(ParserUtil.parseCcas(ccaSet));
    }

```
###### /java/seedu/address/logic/parser/EditGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditGoalCommand object
 */
public class EditGoalCommandParser implements Parser<EditGoalCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditGoalCommand
     * and returns an EditGoalCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGoalCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GOAL_TEXT, PREFIX_IMPORTANCE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGoalCommand.MESSAGE_USAGE));
        }

        EditGoalDescriptor editGoalDescriptor = new EditGoalDescriptor();
        try {
            ParserUtil.parseGoalText(argMultimap.getValue(PREFIX_GOAL_TEXT)).ifPresent(editGoalDescriptor::setGoalText);
            ParserUtil.parseImportance(argMultimap.getValue(PREFIX_IMPORTANCE))
                    .ifPresent(editGoalDescriptor::setImportance);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editGoalDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditGoalCommand.MESSAGE_NOT_EDITED);
        }

        return new EditGoalCommand(index, editGoalDescriptor);
    }
}
```
###### /java/seedu/address/logic/parser/OngoingGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new OngoingGoalCommand object
 */
public class OngoingGoalCommandParser implements Parser<OngoingGoalCommand> {

    public static final boolean ONGOING_BOOLEAN_VALUE = false;
    /**
     * Parses the given {@code String} of arguments in the context of the OngoingGoalCommand
     * and returns an OngoingGoalCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OngoingGoalCommand parse(String args) throws ParseException {

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OngoingGoalCommand.MESSAGE_USAGE));
        }

        OngoingGoalDescriptor ongoingGoalDescriptor = new OngoingGoalDescriptor();

        Optional<String> empty = Optional.empty();
        Completion completion = new Completion(ONGOING_BOOLEAN_VALUE);
        EndDateTime endDateTime = new EndDateTime("");
        ongoingGoalDescriptor.setCompletion(completion);
        ongoingGoalDescriptor.setEndDateTime(endDateTime);


        return new OngoingGoalCommand(index, ongoingGoalDescriptor);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String birthday} into an {@code birthday}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Birthday parseBirthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!Birthday.isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        return new Birthday(trimmedBirthday);
    }

    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(parseBirthday(birthday.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String levelOfFriendship} into a {@code LevelOfFriendship}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code levelOfFriendship} is invalid.
     */
    public static LevelOfFriendship parseLevelOfFriendship(String levelOfFriendship) throws IllegalValueException {
        requireNonNull(levelOfFriendship);
        String trimmedLevelOfFriendship = levelOfFriendship.trim();
        if (!LevelOfFriendship.isValidLevelOfFriendship(trimmedLevelOfFriendship)) {
            throw new IllegalValueException(LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        }
        return new LevelOfFriendship(trimmedLevelOfFriendship);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LevelOfFriendship> parseLevelOfFriendship(Optional<String> levelOfFriendship)
            throws IllegalValueException {
        requireNonNull(levelOfFriendship);
        return levelOfFriendship.isPresent() ? Optional.of(parseLevelOfFriendship(levelOfFriendship.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String unitNumber} into an {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unitNumber} is invalid.
     */
    public static UnitNumber parseUnitNumber(String unitNumber) throws IllegalValueException {
        requireNonNull(unitNumber);
        String trimmedUnitNumber = unitNumber.trim();
        if (!UnitNumber.isValidUnitNumber(trimmedUnitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);
        }
        return new UnitNumber(trimmedUnitNumber);
    }

    /**
     * Parses a {@code Optional<String> unitNumber} into an {@code Optional<UnitNumber>} if {@code unitNumber}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<UnitNumber> parseUnitNumber(Optional<String> unitNumber) throws IllegalValueException {
        requireNonNull(unitNumber);
        return unitNumber.isPresent() ? Optional.of(parseUnitNumber(unitNumber.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String cca} into a {@code Cca}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code cca} is invalid.
     */
    public static Cca parseCca(String cca) throws IllegalValueException {
        requireNonNull(cca);
        String trimmedCca = cca.trim();
        if (!Cca.isValidCcaName(trimmedCca)) {
            throw new IllegalValueException(Cca.MESSAGE_CCA_CONSTRAINTS);
        }
        return new Cca(trimmedCca);
    }

    /**
     * Parses {@code Collection<String> ccas} into a {@code Set<Cca>}.
     */
    public static Set<Cca> parseCcas(Collection<String> ccas) throws IllegalValueException {
        requireNonNull(ccas);
        final Set<Cca> ccaSet = new HashSet<>();
        for (String ccaName : ccas) {
            ccaSet.add(parseCca(ccaName));
        }
        return ccaSet;
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String importance} into an {@code Importance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code importance} is invalid.
     */
    public static Importance parseImportance(String importance) throws IllegalValueException {
        requireNonNull(importance);
        String trimmedImportance = importance.trim();
        if (!Importance.isValidImportance(trimmedImportance)) {
            throw new IllegalValueException(Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);
        }
        return new Importance(trimmedImportance);
    }

    /**
     * Parses a {@code Optional<String> importance} into an {@code Optional<Importance>} if {@code importance}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Importance> parseImportance(Optional<String> importance) throws IllegalValueException {
        requireNonNull(importance);
        return importance.isPresent() ? Optional.of(parseImportance(importance.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String goalText} into an {@code GoalText}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code goalText} is invalid.
     */
    public static GoalText parseGoalText(String goalText) throws IllegalValueException {
        requireNonNull(goalText);
        String trimmedGoalText = goalText.trim();
        if (!GoalText.isValidGoalText(trimmedGoalText)) {
            throw new IllegalValueException(GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);
        }
        return new GoalText(trimmedGoalText);
    }

    /**
     * Parses a {@code Optional<String> goalText} into an {@code Optional<GoalText>} if {@code goalText}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GoalText> parseGoalText(Optional<String> goalText) throws IllegalValueException {
        requireNonNull(goalText);
        return goalText.isPresent() ? Optional.of(parseGoalText(goalText.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String sortField} and checks if it is a valid sortField parameter.
     *
     * @throws IllegalValueException if specified String is an invalid field.
     */
    public static String parseSortGoalField(String sortField) throws IllegalValueException {
        String trimmedSortField = sortField.trim();
        switch (trimmedSortField) {
        case "importance":
        case "completion":
        case "startdatetime":
            return trimmedSortField;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_SORT_FIELD);
        }
    }

    /**
     * Parses a {@code Optional<String> sortField} into an {@code Optional<String>} if {@code sortField}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseSortGoalField(Optional<String> sortField) throws IllegalValueException {
        requireNonNull(sortField);
        return sortField.isPresent() ? Optional.of(parseSortGoalField(sortField.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String order} and check if it is a valid order parameter.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code order} is invalid.
     */
    public static String parseSortGoalOrder(String order) throws IllegalValueException {
        requireNonNull(order);
        String trimmedOrder = order.trim();
        switch (trimmedOrder) {
        case "ascending":
        case "descending":
            return trimmedOrder;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_ORDER_FIELD);
        }
    }

    /**
     * Parses a {@code Optional<String> sortOrder} into an {@code Optional<String>} if {@code sortOrder}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseSortGoalOrder(Optional<String> sortOrder) throws IllegalValueException {
        requireNonNull(sortOrder);
        return sortOrder.isPresent() ? Optional.of(parseSortGoalOrder(sortOrder.get())) : Optional.empty();
    }
}
```
###### /java/seedu/address/logic/parser/SortGoalCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortGoalCommand object
 */
public class SortGoalCommandParser implements Parser<SortGoalCommand> {

    @Override
    public SortGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_FIELD, PREFIX_SORT_ORDER);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_FIELD, PREFIX_SORT_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE));
        }
        try {
            String sortField = ParserUtil.parseSortGoalField(argMultimap.getValue(PREFIX_SORT_FIELD)).get();
            String sortOrder = ParserUtil.parseSortGoalOrder(argMultimap.getValue(PREFIX_SORT_ORDER)).get();
            return new SortGoalCommand(sortField, sortOrder);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortGoalCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }
        if (!isValidThemeColour(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_INVALID_THEME_COLOUR));

        }
        return new ThemeCommand(trimmedArgs);
    }

    /**
     *
     * @param themeColour
     * @return
     */
    private boolean isValidThemeColour(String themeColour) {
        HashMap<String, String> themes = getThemeHashMap();
        if (themes.containsKey(themeColour.toLowerCase())) {
            return true;
        } else {
            return false;
        }

    }
}

```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setCcas(Set<Cca> ccas) {
        this.ccas.setCcas(ccas); }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setGoals(List<Goal> goals) throws DuplicateGoalException {
        this.goals.setGoals(goals);
    }


    public void setReminders(List<Reminder> reminders) throws DuplicateReminderException {
        this.reminders.setReminders(reminders);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes all {@code Ccas}s that are not used by any {@code Person} in this {@code AddressBook}.
     */
    private void removeUnusedCcas() {
        Set<Cca> ccasInPersons = persons.asObservableList().stream()
                .map(Person::getCcas)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        ccas.setCcas(ccasInPersons);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     *  Updates the master cca list to include ccas in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every cca in this person points to a Cca object in the master
     *  list.
     */
    private Person syncWithMasterCcaList(Person person) {
        final UniqueCcaList personCcas = new UniqueCcaList(person.getCcas());
        ccas.mergeFrom(personCcas);

        // Create map with values = cca object references in the master list
        // used for checking person cca references
        final Map<Cca, Cca> masterCcaObjects = new HashMap<>();
        ccas.forEach(cca -> masterCcaObjects.put(cca, cca));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Cca> correctCcaReferences = new HashSet<>();
        personCcas.forEach(cca -> correctCcaReferences.add(masterCcaObjects.get(cca)));
        return new Person(
                person.getName(), person.getPhone(), person.getBirthday(),
                person.getLevelOfFriendship(),  person.getUnitNumber(), correctCcaReferences, person.getMeetDate(),
                person.getTags());
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void addCca(Cca cca) throws UniqueCcaList.DuplicateCcaException {
        ccas.add(cca);
    }

    //// tag-level operations

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Adds a goal to CollegeZone.
     * @throws DuplicateGoalException if an equivalent goal already exists.
     */
    public void addGoal(Goal g) throws DuplicateGoalException {
        goals.add(g);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws GoalNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeGoal(Goal key) throws GoalNotFoundException {
        if (goals.remove(key)) {
            return true;
        } else {
            throw new GoalNotFoundException();
        }
    }

    /**
     * Replaces the given goal {@code target} in the list with {@code editedGoal}.
     *
     * @throws DuplicateGoalException if updating the goal's details causes the goal to be equivalent to
     *      another existing goal in the list.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void updateGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException {
        requireNonNull(editedGoal);
        goals.setGoal(target, editedGoal);
    }

    /**
     * Replaces the given goal {@code target} in the list with {@code editedGoal}.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    public void updateGoalWithoutParameters(Goal target, Goal editedGoal) throws GoalNotFoundException {
        requireNonNull(editedGoal);
        goals.setGoalWithoutParameters(target, editedGoal);
    }

    /**
     * Sorts goal based on the sort field and sort order input.
     */
    public void sortGoal(String sortField, String sortOrder) throws EmptyGoalListException {
        requireNonNull(sortField);
        requireNonNull(sortOrder);
        if (goals.getSize() > 0) {
            goals.sortGoal(sortField, sortOrder);
        } else {
            throw new EmptyGoalListException();
        }
    }
    //// reminder-level operations

```
###### /java/seedu/address/model/goal/Completion.java
``` java
/**
 * Represents a Goal's completion status in the Goals Page.
 */
public class Completion {
    public final String value;
    public final boolean hasCompleted;

    /**
     * Constructs a {@code Completion}.
     *
     * @param isCompleted A valid boolean.
     */
    public Completion(Boolean isCompleted) {
        requireNonNull(isCompleted);
        if (isCompleted) {
            this.hasCompleted = true;
            this.value = "true";
        } else {
            this.hasCompleted = false;
            this.value = "false";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Completion // instanceof handles nulls
                && this.value.equals(((Completion) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/goal/EndDateTime.java
``` java
/**
 * Represents a Goal's end date and time in the Goals Page.
 * Guarantees: immutable; is valid
 */
public class EndDateTime {

    public final String value;
    public final LocalDateTime localDateTimeValue;
    /**
     * Constructs a {@code EndDateTime}.
     *
     * @param endDateTime A valid endDateTime number.
     */
    public EndDateTime(String endDateTime) {
        if (endDateTime.equals("")) {
            this.value = "";
            this.localDateTimeValue = null;
        } else {
            LocalDateTime localEndDateTime = nattyDateAndTimeParser(endDateTime).get();
            this.value = properDateTimeFormat(localEndDateTime);
            this.localDateTimeValue = localEndDateTime;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.value.equals(((EndDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/goal/exceptions/DuplicateGoalException.java
``` java
/**
 * Signals that the operation will result in duplicate Goal objects.
 */
public class DuplicateGoalException extends DuplicateDataException {
    public DuplicateGoalException() {
        super("Operation would result in duplicate goals");
    }
}
```
###### /java/seedu/address/model/goal/exceptions/EmptyGoalListException.java
``` java
/**
 * Signals that the current goal list is empty.
 */
public class EmptyGoalListException extends Exception {
}
```
###### /java/seedu/address/model/goal/exceptions/GoalNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified goal.
 */
public class GoalNotFoundException extends Exception {}
```
###### /java/seedu/address/model/goal/Goal.java
``` java
/**
 * Represents a Goal in the Goals Page.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Goal {

    private final Importance importance;
    private final GoalText goalText;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final Completion completion;

    /**
     * Every field must be present and not null.
     */

    public Goal(Importance importance, GoalText goalText, StartDateTime startDateTime,
                  EndDateTime endDateTime, Completion completion) {
        requireAllNonNull(importance, goalText, startDateTime, completion);
        this.importance = importance;
        this.goalText = goalText;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.completion = completion;
    }

    public Importance getImportance() {
        return importance;
    }

    public GoalText getGoalText() {
        return goalText;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    public Completion getCompletion() {
        return completion;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Goal)) {
            return false;
        }

        Goal otherPerson = (Goal) other;
        return otherPerson.getImportance().equals(this.getImportance())
                && otherPerson.getGoalText().equals(this.getGoalText());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(importance, goalText, startDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Importance: ")
                .append(getImportance())
                .append(" Goal Text: ")
                .append(getGoalText())
                .append(" Start Date Time: ")
                .append(getStartDateTime());

        return builder.toString();
    }

}
```
###### /java/seedu/address/model/goal/GoalText.java
``` java
/**
 * Represents a Goal's text in the Goals Page.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoalText(String)}
 */
public class GoalText {


    public static final String MESSAGE_GOAL_TEXT_CONSTRAINTS =
            "Goal text can be any expression that are not just whitespaces.";
    public static final String GOAL_TEXT_VALIDATION_REGEX = "^(?!\\s*$).+";
    public final String value;

    /**
     * Constructs a {@code GoalText}.
     *
     * @param goalText A valid goal text.
     */
    public GoalText(String goalText) {
        requireNonNull(goalText);
        checkArgument(isValidGoalText(goalText), MESSAGE_GOAL_TEXT_CONSTRAINTS);
        this.value = goalText;
    }

    /**
     * Returns true if a given string is a valid goal text.
     */
    public static boolean isValidGoalText(String test) {
        return test.matches(GOAL_TEXT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalText // instanceof handles nulls
                && this.value.equals(((GoalText) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/goal/Importance.java
``` java
/**
 * Represents a Goal's importance in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidImportance(String)}
 */
public class Importance implements Comparable<Importance> {


    public static final String MESSAGE_IMPORTANCE_CONSTRAINTS =
            "Importance should only be a numerical integer value between 1 to 10.";
    public static final String IMPORTANCE_VALIDATION_REGEX = "[0-9]+";
    private static final int MINIMUM_IMPORTANCE = 1;
    private static final int MAXIMUM_IMPORTANCE = 10;
    private static int importanceInIntegerForm;
    public final String value;

    /**
     * Constructs a {@code Importance}.
     *
     * @param importance A valid importance.
     */
    public Importance(String importance) {
        requireNonNull(importance);
        checkArgument(isValidImportance(importance), MESSAGE_IMPORTANCE_CONSTRAINTS);
        this.value = importance;
    }

    /**
     * Returns true if a given string is a valid goal importance.
     */
    public static boolean isValidImportance(String test) {
        return test.matches(IMPORTANCE_VALIDATION_REGEX) && isAnIntegerWithinRange(test);
    }

    /**
     * Returns true if a given string is an integer and within range of importance.
     */
    private static boolean isAnIntegerWithinRange(String test) {
        importanceInIntegerForm = Integer.parseInt(test);
        if (importanceInIntegerForm >= MINIMUM_IMPORTANCE
                && importanceInIntegerForm <= MAXIMUM_IMPORTANCE) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Importance // instanceof handles nulls
                && this.value.equals(((Importance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Importance importance) {
        if ((Integer.valueOf(importance.value)).equals(Integer.valueOf(this.value))) {
            return 0;
        } else if ((Integer.valueOf(importance.value)) < (Integer.valueOf(this.value))) {
            return 1;
        } else if ((Integer.valueOf(importance.value)) > (Integer.valueOf(this.value))) {
            return -1;
        }
        return 0;
    }
}
```
###### /java/seedu/address/model/goal/StartDateTime.java
``` java
/**
 * Represents a Goal's start date in the address book.
 */
public class StartDateTime implements Comparable<StartDateTime> {

    public final String value;
    public final LocalDateTime localDateTimeValue;


    /**
     * Constructs a {@code StartDateTime}.
     *
     * @param startDateTime A valid LocalDateTime.
     */
    public StartDateTime(LocalDateTime startDateTime) {
        requireNonNull(startDateTime);
        this.localDateTimeValue = startDateTime;
        this.value = properDateTimeFormat(startDateTime);
    }

    public StartDateTime(String startDateTimeInString) {
        requireNonNull(startDateTimeInString);
        this.value = startDateTimeInString;
        this.localDateTimeValue = getLocalDateTimeFromProperDateTime(startDateTimeInString);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                && this.value.equals(((StartDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(StartDateTime startDateTime) {
        if ((startDateTime.localDateTimeValue).isBefore(this.localDateTimeValue)) {
            return 1;
        } else if ((startDateTime.localDateTimeValue).isAfter(this.localDateTimeValue)) {
            return -1;
        }
        return 0;
    }
}
```
###### /java/seedu/address/model/goal/UniqueGoalList.java
``` java
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
     * Returns the size of goal list.
     */
    public int getSize() {
        return internalList.size();
    }

    /**
     * Sort goals internal list using comparator
     * @param sortField
     */
    public void sortGoal(String sortField, String sortOrder) throws EmptyGoalListException {
        String sortFieldAndOrder = sortField + " " + sortOrder;
        //Comparator<Goal> comparatorImportance = Comparator.comparingInt(Goal::getImportance);
        switch (sortFieldAndOrder) {
        case "importance ascending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) ->goalA.getImportance()
                    .compareTo(goalB.getImportance()));
            break;
        case "importance descending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) ->goalB.getImportance()
                    .compareTo(goalA.getImportance()));
            break;
        case "completion ascending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) -> new Boolean(goalA.getCompletion().hasCompleted)
                    .compareTo(goalB.getCompletion().hasCompleted));
            break;
        case "completion descending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) -> new Boolean(goalB.getCompletion().hasCompleted)
                    .compareTo(goalA.getCompletion().hasCompleted));
            break;
        case "startdatetime ascending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) ->goalA.getStartDateTime()
                    .compareTo(goalB.getStartDateTime()));
            break;
        case "startdatetime descending":
            FXCollections.sort(internalList, (Goal goalA, Goal goalB) ->goalB.getStartDateTime()
                    .compareTo(goalA.getStartDateTime()));
            break;

        default:
            break;
        }
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
```
###### /java/seedu/address/model/Model.java
``` java
    /** Adds the given goal */
    void addGoal(Goal goal) throws DuplicateGoalException;

    /** Returns an unmodifiable view of the filtered goal list */
    ObservableList<Goal> getFilteredGoalList();

    /**
     * Replaces the given goal {@code target} with {@code editedGoal}.
     *
     * @throws DuplicateGoalException if updating the goal's details causes the goal to be equivalent to
     *      another existing goal in the list.
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    void updateGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException;

    /**
     * Updates the filter of the filtered goal list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGoalList(Predicate<Goal> predicate);

    /** Deletes the given goal. */
    void deleteGoal(Goal target) throws GoalNotFoundException;

    /**
     * Replaces the given goal {@code target} with {@code updateGoal}.
     *
     * @throws GoalNotFoundException if {@code target} could not be found in the list.
     */
    void updateGoalWithoutParameters(Goal target, Goal editedGoal) throws GoalNotFoundException;

    /**
     * Sort the goal based on sortType
     */
    void sortGoal(String sortType, String sortOrder) throws EmptyGoalListException;

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void addGoal(Goal goal) throws DuplicateGoalException {
        addressBook.addGoal(goal);
        updateFilteredGoalList(PREDICATE_SHOW_ALL_GOALS);
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Goal> getFilteredGoalList() {
        return FXCollections.unmodifiableObservableList(filteredGoals);
    }

    @Override
    public void updateFilteredGoalList(Predicate<Goal> predicate) {
        requireNonNull(predicate);
        filteredGoals.setPredicate(predicate);
    }

    @Override
    public synchronized void deleteGoal(Goal target) throws GoalNotFoundException {
        addressBook.removeGoal(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateGoal(Goal target, Goal editedGoal)
            throws DuplicateGoalException, GoalNotFoundException {
        requireAllNonNull(target, editedGoal);

        addressBook.updateGoal(target, editedGoal);
        indicateAddressBookChanged();
    }

    @Override
    public void updateGoalWithoutParameters(Goal target, Goal editedGoal)
            throws GoalNotFoundException {
        requireAllNonNull(target, editedGoal);

        addressBook.updateGoalWithoutParameters(target, editedGoal);
        indicateAddressBookChanged();
    }

    @Override
    public void sortGoal(String sortGoalType, String sortGoalOrder) throws EmptyGoalListException {
        requireAllNonNull(sortGoalType, sortGoalOrder);
        addressBook.sortGoal(sortGoalType, sortGoalOrder);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/person/Birthday.java
``` java
/**
 * Represents a Person's birthday in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person birthday should be a valid date.";
    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)"
                    + "\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)"
                    + "\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|"
                    + "[3579][26])00))))$";

    public final String value;

    /**
     * Constructs an {@code Birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        this.value = birthday;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Cca.java
``` java
/**
 * Represents a Person's CCAs in CollegeZone.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaName(String)}
 */
public class Cca {

    public static final String MESSAGE_CCA_CONSTRAINTS = "CCAs should be in alphanumeric";
    public static final String CCA_VALIDATION_REGEX = "\\s*\\p{Alnum}[\\p{Alnum}\\s]*";
    public final String ccaName;

    /**
     * Constructs a {@code CCA}.
     *
     * @param ccaName A valid CCA.
     */
    public Cca(String ccaName) {
        requireNonNull(ccaName);
        checkArgument(isValidCcaName(ccaName), MESSAGE_CCA_CONSTRAINTS);
        this.ccaName = ccaName;
    }

    /**
     * Returns true if a given string is a valid CCA name.
     */
    public static boolean isValidCcaName(String test) {
        return test.matches(CCA_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cca // instanceof handles nulls
                && this.ccaName.equals(((Cca) other).ccaName)); // state check
    }

    @Override
    public int hashCode() {
        return ccaName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + ccaName + ']';
    }

}
```
###### /java/seedu/address/model/person/LevelOfFriendship.java
``` java
/**
 * Represents a Person's Level of Friendship in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevelOfFriendship(String)}
 */
public class LevelOfFriendship {

    public static final String MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS =
            "Level of Friendship should only be a numerical integer value between 1 to 10";
    public static final String LEVEL_OF_FRIENDSHIP_VALIDATION_REGEX = "[0-9]+";
    private static final int MINIMUM_LEVEL_OF_FRIENDSHIP = 1;
    private static final int MAXIMUM_LEVEL_OF_FRIENDSHIP = 10;
    private static int levelOfFriendshipInIntegerForm;
    public final String value;

    /**
     * * Constructs an {@code LevelOfFriendship}.
     *
     * @param levelOfFriendship A valid level of friendship number.
     */
    public LevelOfFriendship(String levelOfFriendship) {
        requireNonNull(levelOfFriendship);
        checkArgument(isValidLevelOfFriendship(levelOfFriendship), MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        this.value = levelOfFriendship;
    }

    /**
     * Returns true if a given string is a valid person level of friendship.
     */
    public static boolean isValidLevelOfFriendship(String test) {

        return test.matches(LEVEL_OF_FRIENDSHIP_VALIDATION_REGEX) && isAnIntegerWithinRange(test);
    }

    /**
     * Returns true if a given string is an integer and within range of level of friendship.
     */
    private static boolean isAnIntegerWithinRange(String test) {
        levelOfFriendshipInIntegerForm = Integer.parseInt(test);
        if (levelOfFriendshipInIntegerForm >= MINIMUM_LEVEL_OF_FRIENDSHIP
                && levelOfFriendshipInIntegerForm <= MAXIMUM_LEVEL_OF_FRIENDSHIP) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LevelOfFriendship // instanceof handles nulls
                && this.value.equals(((LevelOfFriendship) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/person/UniqueCcaList.java
``` java
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
```
###### /java/seedu/address/model/person/UnitNumber.java
``` java
    /**
     * * Constructs an {@code UnitNumber}.
     *
     * @param unitNumber A valid unit number.
     */
    public UnitNumber(String unitNumber) {
        requireNonNull(unitNumber);
        checkArgument(isValidUnitNumber(unitNumber), MESSAGE_UNIT_NUMBER_CONSTRAINTS);
        this.value = unitNumber;
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnitNumber(String test) {

        return test.matches(UNIT_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

```
###### /java/seedu/address/model/person/UnitNumber.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnitNumber // instanceof handles nulls
                && this.value.equals(((UnitNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the ccas list.
     * This list will not contain any duplicate ccas.
     */
    ObservableList<Cca> getCcaList();

```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the goals list.
     * This list will not contain any duplicate goals.
     */
    ObservableList<Goal> getGoalList();
```
###### /java/seedu/address/model/ThemeColourUtil.java
``` java
/**
 * Contains utility methods for ThemeColour.
 */
public class ThemeColourUtil {

    private static final HashMap<String, String> themes;

    static {
        themes = new HashMap<>();
        themes.put("light", "view/LightTheme.css");
        themes.put("bubblegum", "view/BubblegumTheme.css");
        themes.put("dark", "view/DarkTheme.css");
    }

    public static HashMap<String, String> getThemeHashMap() {
        return themes;
    }
}
```
###### /java/seedu/address/model/util/SampleCollegeZone.java
``` java
/**
 * Contains method to get a sample CollegeZone data
 */
public class SampleCollegeZone {

    public static ReadOnlyAddressBook getSampleCollegeZone() {
        AddressBook sampleCz = new AddressBook();
        try {
            for (Person samplePerson : getSamplePersons()) {
                sampleCz.addPerson(samplePerson);
            }
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
        try {
            for (Goal sampleGoal : getSampleGoals()) {
                sampleCz.addGoal(sampleGoal);
            }
        } catch (DuplicateGoalException e) {
            throw new AssertionError("sample data cannot contain duplicate goals", e);
        }
        try {
            for (Reminder sampleReminder : getSampleReminders()) {
                sampleCz.addReminder(sampleReminder);
            }
        } catch (DuplicateReminderException e) {
            throw new AssertionError("sample data cannot contain duplicate reminders", e);
        }
        return sampleCz;
    }
}
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
/**
 * Contains utility methods for populating {@code CollegeZone} with sample data.
 */
public class SampleDataUtil {

    public static final Meet EMPTY_MEET_DATE = new Meet("15/04/2018");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("01/01/1997"),
                new LevelOfFriendship("5"), new UnitNumber("#06-40"), getCcaSet("Basketball"),
                EMPTY_MEET_DATE, getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Birthday("21/02/1990"),
                new LevelOfFriendship("9"), new UnitNumber("#07-18"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Birthday("05/09/1980"),
                new LevelOfFriendship("1"), new UnitNumber("#11-04"), getCcaSet("Swimming"),
                EMPTY_MEET_DATE, getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Birthday("20/02/1995"),
                new LevelOfFriendship("6"), new UnitNumber("#16-43"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Birthday("01/01/1999"),
                new LevelOfFriendship("7"), new UnitNumber("#16-41"), getCcaSet(),
                EMPTY_MEET_DATE, getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Birthday("02/04/1995"),
                new LevelOfFriendship("10"), new UnitNumber("#6-43"), getCcaSet("Computing club", "Anime Club"),
                EMPTY_MEET_DATE, getTagSet("colleagues")),
            new Person(new Name("Deborah Low"), new Phone("91162930"), new Birthday("24/05/1997"),
                    new LevelOfFriendship("9"), new UnitNumber("#10-24"), getCcaSet("Aerobics Cub"),
                    EMPTY_MEET_DATE, getTagSet("colleagues")),
            new Person(new Name("Royce Lew"), new Phone("93265932"), new Birthday("10/04/1996"),
                    new LevelOfFriendship("5"), new UnitNumber("#02-021"), getCcaSet(),
                    EMPTY_MEET_DATE, getTagSet("boyfriend")),
            new Person(new Name("Kaden Yeo"), new Phone("82350332"), new Birthday("28/03/2001"),
                    new LevelOfFriendship("6"), new UnitNumber("#6-20"), getCcaSet("shooting"),
                    EMPTY_MEET_DATE, getTagSet("friends")),
            new Person(new Name("Matthew Chiang"), new Phone("92624417"), new Birthday("02/04/1995"),
                    new LevelOfFriendship("4"), new UnitNumber("#20-43"), getCcaSet("Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("classmate")),
            new Person(new Name("Loh Sin Yuen"), new Phone("92624417"), new Birthday("02/05/1995"),
                    new LevelOfFriendship("10"), new UnitNumber("#03-63"), getCcaSet("dance"),
                    EMPTY_MEET_DATE, getTagSet("schoolmate")),
            new Person(new Name("Florence Chiang"), new Phone("92624417"), new Birthday("02/06/1995"),
                    new LevelOfFriendship("10"), new UnitNumber("#6-97"), getCcaSet("volleyball"),
                    EMPTY_MEET_DATE, getTagSet("bff")),
            new Person(new Name("Daniel Low"), new Phone("92624417"), new Birthday("12/04/1995"),
                    new LevelOfFriendship("1"), new UnitNumber("#7-473"), getCcaSet("Muay Thai"),
                    EMPTY_MEET_DATE, getTagSet("cousin")),
            new Person(new Name("Rachel Lee Yan Ling"), new Phone("92624417"), new Birthday("23/04/1995"),
                    new LevelOfFriendship("3"), new UnitNumber("#6-69"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("cousin")),
            new Person(new Name("Sarah tan"), new Phone("92624417"), new Birthday("27/04/1999"),
                    new LevelOfFriendship("2"), new UnitNumber("#8-43"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet()),
            new Person(new Name("Amanda Soh"), new Phone("92624417"), new Birthday("02/12/1995"),
                    new LevelOfFriendship("1"), new UnitNumber("#24-579"), getCcaSet("Computing club", "Anime Club"),
                    EMPTY_MEET_DATE, getTagSet("exgirlfriend")),
            new Person(new Name("Marlene Koh"), new Phone("92624417"), new Birthday("02/07/1997"),
                    new LevelOfFriendship("10"), new UnitNumber("#02-222"), getCcaSet("Pool"),
                    EMPTY_MEET_DATE, getTagSet("closefriend")),
            new Person(new Name("Johnny Depp"), new Phone("92624417"), new Birthday("02/12/1994"),
                    new LevelOfFriendship("2"), new UnitNumber("#01-346"), getCcaSet("Pool"),
                    EMPTY_MEET_DATE, getTagSet("malafriend")),
            new Person(new Name("Aditya"), new Phone("92624417"), new Birthday("02/04/1998"),
                    new LevelOfFriendship("3"), new UnitNumber("#6-43"), getCcaSet(),
                    EMPTY_MEET_DATE, getTagSet("malafriend")),
            new Person(new Name("Fuad"), new Phone("92624417"), new Birthday("20/04/1995"),
                    new LevelOfFriendship("9"), new UnitNumber("#6-43"), getCcaSet("Floorball"),
                    EMPTY_MEET_DATE, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a cca set containing the list of strings given.
     */
    public static Set<Cca> getCcaSet(String... strings) {
        HashSet<Cca> ccas = new HashSet<>();
        for (String s : strings) {
            ccas.add(new Cca(s));
        }

        return ccas;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### /java/seedu/address/model/util/SampleGoalDataUtil.java
``` java
/**
 * Contains utility methods for populating {@code CollegeZone} with sample data.
 */
public class SampleGoalDataUtil {

    public static final EndDateTime EMPTY_END_DATE_TIME = new EndDateTime("");

    public static Goal[] getSampleGoals() {
        return new Goal[] {

            new Goal(new Importance("1"), new GoalText("finish cs2103"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("2"), new GoalText("no"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-08 12:12")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("3"), new GoalText("grow taller"),
                    new StartDateTime(getLocalDateTimeFromString("1997-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("3"), new GoalText("finish cs2105 assignments"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-08 10:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("1"), new GoalText("learning digital art"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:39")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("2"), new GoalText("finish cs2103!!!!"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("10"), new GoalText("finish cs2103!!!!"),
                    new StartDateTime(getLocalDateTimeFromString("2018-03-18 08:30")),
                    new EndDateTime("03/04/2018 12:30"), new Completion(true)),
            new Goal(new Importance("6"), new GoalText("lose 0.5kg by this week"),
                    new StartDateTime(getLocalDateTimeFromString("2018-04-06 19:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("10"), new GoalText("Find love <3"),
                    new StartDateTime(getLocalDateTimeFromString("2014-04-08 20:30")),
                    new EndDateTime("02/02/2018 12:30"), new Completion(true)),
            new Goal(new Importance("7"), new GoalText("water plants"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("5"), new GoalText("buy dog food"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("4"), new GoalText("Take the stairs more often!"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("04/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("10"), new GoalText("Eat PGP MALA once every week"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("07/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("7"), new GoalText("Make more friends in uni"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:45")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("9"), new GoalText("Go CCA regularly"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 13:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("9"), new GoalText("Drink 8 cups of water everyday"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 01:59")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("8"), new GoalText("Get A for CS2105"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 02:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("8"), new GoalText("Get A- for GEH1036"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 03:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
            new Goal(new Importance("10"), new GoalText("Aim to increase CAP by 0.2 by the end of this semester"),
                    new StartDateTime(getLocalDateTimeFromString("2017-02-18 12:30")),
                    EMPTY_END_DATE_TIME, new Completion(false)),
            new Goal(new Importance("4"), new GoalText("Do 50 squats EVERYDAY"),
                    new StartDateTime(getLocalDateTimeFromString("2017-04-08 12:30")),
                    new EndDateTime("03/06/2018 12:30"), new Completion(true)),
        };
    }

    public static ReadOnlyAddressBook getSampleGoalAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Goal sampleGoal : getSampleGoals()) {
                sampleAb.addGoal(sampleGoal);
            }
            return sampleAb;
        } catch (DuplicateGoalException e) {
            throw new AssertionError("sample data cannot contain duplicate goals", e);
        }
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedCca.java
``` java
/**
 * JAXB-friendly adapted version of the Cca.
 */
public class XmlAdaptedCca {

    @XmlValue
    private String ccaName;

    /**
     * Constructs an XmlAdaptedCca.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCca() {}

    /**
     * Constructs a {@code XmlAdaptedCca} with the given {@code ccaName}.
     */
    public XmlAdaptedCca(String ccaName) {
        this.ccaName = ccaName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCca(Cca source) {
        ccaName = source.ccaName;
    }

    /**
     * Converts this jaxb-friendly adapted cca object into the model's Cca object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Cca toModelType() throws IllegalValueException {
        if (!Cca.isValidCcaName(ccaName)) {
            throw new IllegalValueException(Cca.MESSAGE_CCA_CONSTRAINTS);
        }
        return new Cca(ccaName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCca)) {
            return false;
        }

        return ccaName.equals(((XmlAdaptedCca) other).ccaName);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedGoal.java
``` java
/**
 * JAXB-friendly version of the Goal.
 */
public class XmlAdaptedGoal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Goal's %s field is missing!";

    @XmlElement(required = true)
    private String importance;
    @XmlElement(required = true)
    private String goalText;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String completion;

    /**
     * Constructs an XmlAdaptedGoal.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGoal() {}

    /**
     * Constructs an {@code XmlAdaptedGoal} with the given goal details.
     */
    public XmlAdaptedGoal(String importance, String goalText, String startDateTime, String endDateTime,
                          String completion) {
        this.importance = importance;
        this.goalText = goalText;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.completion = completion;
    }

    /**
     * Converts a given Goal into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGoal
     */
    public XmlAdaptedGoal(Goal source) {
        importance = source.getImportance().value;
        goalText = source.getGoalText().value;
        startDateTime = source.getStartDateTime().value;
        endDateTime = source.getEndDateTime().value;
        completion = source.getCompletion().value;
    }

    /**
     * Converts this jaxb-friendly adapted goal object into the model's Goal object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted goal
     */
    public Goal toModelType() throws IllegalValueException {

        if (this.importance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Importance.class.getSimpleName()));
        }
        if (!Importance.isValidImportance(this.importance)) {
            throw new IllegalValueException(Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);
        }
        final Importance importance = new Importance(this.importance);

        if (this.goalText == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GoalText.class.getSimpleName()));
        }
        if (!GoalText.isValidGoalText(this.goalText)) {
            throw new IllegalValueException(GoalText.MESSAGE_GOAL_TEXT_CONSTRAINTS);
        }
        final GoalText goalText = new GoalText(this.goalText);

        if (this.startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }

        final StartDateTime startDateTime = new StartDateTime(this.startDateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime
                    .class.getSimpleName()));
        }

        final EndDateTime endDateTime = new EndDateTime(this.endDateTime);

        if (this.completion == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Completion.class.getSimpleName()));
        }

        final Completion completion = new Completion((this.completion.equals("true")));
        return new Goal(importance, goalText, startDateTime, endDateTime, completion);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGoal)) {
            return false;
        }

        XmlAdaptedGoal otherGoal = (XmlAdaptedGoal) other;
        return Objects.equals(importance, otherGoal.importance)
                && Objects.equals(goalText, otherGoal.goalText)
                && Objects.equals(startDateTime, otherGoal.startDateTime)
                && Objects.equals(endDateTime, otherGoal.endDateTime)
                && Objects.equals(completion, otherGoal.completion);
    }
}

```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        final List<Cca> personCcas = new ArrayList<>();
        for (XmlAdaptedCca cca : ccas) {
            personCcas.add(cca.toModelType());
        }
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        if (this.birthday == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Birthday.class.getSimpleName()));
        }
        if (!Birthday.isValidBirthday(this.birthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        final Birthday birthday = new Birthday(this.birthday);

        if (this.levelOfFriendship == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LevelOfFriendship
                    .class.getSimpleName()));
        }
        if (!LevelOfFriendship.isValidLevelOfFriendship(this.levelOfFriendship)) {
            throw new IllegalValueException(LevelOfFriendship.MESSAGE_LEVEL_OF_FRIENDSHIP_CONSTRAINTS);
        }
        final LevelOfFriendship levelOfFriendship = new LevelOfFriendship(this.levelOfFriendship);

```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        if (this.unitNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitNumber.class.getSimpleName()));
        }
        if (!UnitNumber.isValidUnitNumber(this.unitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_UNIT_NUMBER_CONSTRAINTS);
        }
        final UnitNumber unitNumber = new UnitNumber(this.unitNumber);
        final Set<Cca> ccas = new HashSet<>(personCcas);

```
###### /java/seedu/address/ui/GoalCard.java
``` java
/**
 * An UI component that displays information of a {@code Goal}.
 */
public class GoalCard extends UiPart<Region> {
    private static final int NOT_COMPLETED_COLOUR_STYLE = 0;
    private static final int COMPLETED_COLOUR_STYLE = 1;
    private static final String FXML = "GoalListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Goal goal;

    @FXML
    private HBox goalCardPane;
    @FXML
    private Label goalText;
    @FXML
    private Label id;
    @FXML
    private FlowPane importance;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane completion;


    public GoalCard(Goal goal, int displayedIndex) {
        super(FXML);
        this.goal = goal;
        id.setText(displayedIndex + ". ");
        goalText.setText(goal.getGoalText().value);
        initImportance(goal);
        startDateTime.setText("Start " + goal.getStartDateTime().value);
        if (goal.getEndDateTime().value.equals("")) {
            endDateTime.setText(goal.getEndDateTime().value);
        } else {
            endDateTime.setText("End " + goal.getEndDateTime().value);
        }
        initCompletion(goal);
    }

    /**
     * Creates the completion label for {@code goal}.
     */
    private void initCompletion(Goal goal) {
        String trueOrFalseString = goal.getCompletion().value;
        if (trueOrFalseString.equals("true")) {
            Image completedImage = AppUtil.getImage("/images/completedImage.png");
            ImageView completedImageView = new ImageView(completedImage);
            completedImageView.setFitHeight(30);
            completedImageView.setFitWidth(30);
            Label completionLabel = new Label("Completed", completedImageView);
            completion.getChildren().add(completionLabel);
        } else {
            Image ongoingImage = AppUtil.getImage("/images/ongoingImage.png");
            ImageView ongoingImageView = new ImageView(ongoingImage);
            ongoingImageView.setFitHeight(27);
            ongoingImageView.setFitWidth(27);
            Label completionLabel = new Label("Ongoing", ongoingImageView);
            completion.getChildren().add(completionLabel);
        }
    }

    /**
     * Creates the importance label for {@code goal}.
     */
    private void initImportance(Goal goal) {
        String starValue = changeImportanceToStar(goal.getImportance().value);
        Label importanceLabel = new Label(starValue);
        importanceLabel.getStyleClass().add("yellow");
        importance.getChildren().add(importanceLabel);
    }

    /**
     * Takes in @param value representing the importance value
     * @return a number of star string.
     */
    public static String changeImportanceToStar(String value) {
        int intValue = Integer.parseInt(value);
        String starString = "";
        for (int i = 0; i < intValue; i++) {
            starString = starString + '\u2605' + " ";
        }
        return starString;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GoalCard)) {
            return false;
        }

        // state check
        GoalCard card = (GoalCard) other;
        return id.getText().equals(card.id.getText())
                && goal.equals(card.goal);
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Calculation of percentage of goal completed
     * @return
     */
    private int calculateGoalCompletion() {
        int totalGoal = logic.getFilteredGoalList().size();
        int totalGoalCompleted = 0;
        String completionStatus;
        for (int i = 0; i < totalGoal; i++) {
            completionStatus = logic.getFilteredGoalList().get(i).getCompletion().value;
            totalGoalCompleted += isCompletedGoal(completionStatus);
        }
        int percentageGoalCompletion = (int) (((float) totalGoalCompleted / totalGoal) * PERCENTAGE_KEY_NUMBER);
        return percentageGoalCompletion;
    }

    /**
     * @param completionStatus gives a String that should be either "true" or "false", indicating if the goal is
     *                         completed.
     * @return true or false
     */
    private int isCompletedGoal(String completionStatus) {
        int valueToAdd;
        if (completionStatus.equals("true")) {
            valueToAdd = 1;
        } else {
            valueToAdd = 0;
        }
        return valueToAdd;
    }

    private void setThemeColour() {
        setThemeColour(DEFAULT_THEME_COLOUR);
    }

    private void setThemeColour(String themeColour) {
        primaryStage.getScene().getStylesheets().add(EXTENSIONS_STYLESHEET);
        primaryStage.getScene().getStylesheets().add(themeHashMap.get(themeColour));
    }

    private void changeThemeColour() {
        primaryStage.getScene().getStylesheets().clear();
        setThemeColour(themeColour);
    }

    @Subscribe
    private void handleChangeThemeEvent(ThemeSwitchRequestEvent event) {
        themeColour = event.themeToChangeTo;
        Platform.runLater(
                this::changeThemeColour
        );
    }
}
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between diffe 11rent runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    private String getCcasInString(Set<Cca> ccas) {
        String ccasValue = "";
        Iterator iterator = ccas.iterator();
        while (iterator.hasNext()) {
            ccasValue = ccasValue + iterator.next().toString() + " ";
        }
        return ccasValue;
    }

    /**
     * Takes in @param value representing the level of friendship value
     * @return a number of hearts string.
     */
    public static String changeLevelOfFriendshipToHeart(String value) {
        int intValue = Integer.parseInt(value);
        String heartString = "";
        for (int i = 0; i < intValue; i++) {
            heartString = heartString + '\u2665' + " ";
        }
        return heartString;
    }

```
###### /java/seedu/address/ui/PersonListPanel.java
``` java
    @Subscribe
    private void handleJumpToGoalListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToGoal(event.targetIndex);
    }

    /**
     * Scrolls to the {@code GoalCard} at the {@code index} and selects it.
     */
    private void scrollToGoal(int index) {
        Platform.runLater(() -> {
            goalListView.scrollTo(index);
            goalListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code GoalCard}.
     */
    class GoalListViewCell extends ListCell<GoalCard> {

        @Override
        protected void updateItem(GoalCard goal, boolean empty) {
            super.updateItem(goal, empty);

            if (empty || goal == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(goal.getRoot());
            }
        }
    }
}
```
###### /java/seedu/address/ui/StatusBarFooter.java
``` java
    private void setGoalCompletion(int goalCompletion) {
        Platform.runLater(() -> this.goalCompletionStatus.setText("Goal " + goalCompletion + "% completed."));
    }

    public int getGoalCompletion(ObservableList<Goal> goalList) {
        int totalGoal = goalList.size();
        int totalGoalCompleted = 0;
        String completionStatus;
        for (int i = 0; i < totalGoal; i++) {
            completionStatus = goalList.get(i).getCompletion().value;
            totalGoalCompleted += isCompletedGoal(completionStatus);
        }
        int percentageGoalCompletion = (int) (((float) totalGoalCompleted / totalGoal) * PERCENTAGE_KEY_NUMBER);
        return percentageGoalCompletion;
    }

    /**
     * @param completionStatus gives a String that should be either "true" or "false", indicating if the goal is
     *                         completed.
     * @return 1 or 0
     */
    private int isCompletedGoal(String completionStatus) {
        int valueToAdd;
        if (completionStatus.equals("true")) {
            valueToAdd = 1;
        } else {
            valueToAdd = 0;
        }
        return valueToAdd;
    }
```
###### /resources/view/BubblegumTheme.css
``` css
.background {
    -fx-background-color: derive(#ffdae0, 20%);
    background-color: #ffb6c1;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Lato";
    -fx-text-fill: #FFFF99;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Lato";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Lato Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Lato";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #ffdae0;
    -fx-control-inner-background: #ffdae0;
    -fx-background-color: #ffdae0;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Lato";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#ffdae0, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#ffdae0, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#ffdae0, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #BFEFFF;
}

.list-cell:filled:odd {
    -fx-background-color: #63D1F4;
}

.list-cell:filled:selected {
    -fx-background-color: #	00BFFF;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Lato";
    -fx-font-size: 18px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Lato";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#ffdae0, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#ffdae0, 20%);
     -fx-border-color: derive(#ffdae0, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#ffdae0, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Lato Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Lato";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#ffdae0, 30%);
    -fx-border-color: derive(#ffdae0, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#ffdae0, 30%);
    -fx-border-color: derive(#ffdae0, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#ffdae0, 30%);
}

.context-menu {
    -fx-background-color: derive(#ffdae0, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#ffdae0, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Lato Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}
/*a*/
.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #313131;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #ffdae0;
    -fx-font-family: "Lato";
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #ebebeb;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: #ffdae0;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #ffdae0;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffdae0;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #ffdae0;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #ffdae0;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#ffdae0, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#BA55D3, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#EE82EE, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #ffb6c1 transparent #ffb6c1;
    -fx-background-insets: 0;
    -fx-border-color: #ffb6c1 #ffb6c1 #FF7F50 #ffb6c1;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Lato Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #ffb6c1, transparent, #ffb6c1;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}


#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: #ff7675;
}

#tags .yellow {
    -fx-background-color: #ffeaa7;
    -fx-text-fill: black;
}

#tags .blue {
    -fx-text-fill: black;
    -fx-background-color: #48dbfb;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: #ffa502;
}

#tags .brown {
    -fx-text-fill: black;
    -fx-background-color: #D7ACAC;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: #55efc4;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: #fd79a8;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#tags .purple {
    -fx-text-fill: black;
    -fx-background-color: #a29bfe;
}

#importance {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#importance .label {
    -fx-background-color: #FFE761;
    -fx-text-fill: black;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 3;
    -fx-background-radius: 2;
    -fx-font-size: 13;
}
```
###### /resources/view/GoalListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="goalCardPane" fx:id="goalCardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="200" prefWidth="200" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="id" alignment="TOP_LEFT" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="goalText" styleClass="cell_big_label" text="\$first" wrapText="true" />
      </HBox>
      <Label text=" " />
      <FlowPane fx:id="importance" prefHeight="0.0" prefWidth="180.0" />
      <Label fx:id="startDateTime" styleClass="cell_small_label" text="\$startDateTime" wrapText="true" />
      <Label fx:id="endDateTime" styleClass="cell_small_label" text="\$endDateTime" wrapText="true" />
      <Label text=" " />
      <FlowPane fx:id="completion" alignment="BOTTOM_RIGHT" columnHalignment="RIGHT" prefWidth="130.0" rowValignment="BOTTOM" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### /resources/view/LightTheme.css
``` css
.background {
    -fx-background-color: derive(#ffffff, 20%);
    background-color: #f5f5f5;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Lato";
    -fx-text-fill: #cecece;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Lato";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Lato Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Lato";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #ffffff;
    -fx-control-inner-background: #ffffff;
    -fx-background-color: #ffffff;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Lato";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#ffffff, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#ffffff, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #ebebeb;
}

.list-cell:filled:odd {
    -fx-background-color: #fcf9f9;
}

.list-cell:filled:selected {
    -fx-background-color: #dae3f2;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Lato";
    -fx-font-size: 18px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Lato";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#ffffff, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#ffffff, 20%);
     -fx-border-color: derive(#ffffff, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Lato Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Lato";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#ffffff, 30%);
    -fx-border-color: derive(#ffffff, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#ffffff, 30%);
    -fx-border-color: derive(#ffffff, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#ffffff, 30%);
}

.context-menu {
    -fx-background-color: derive(#ffffff, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#ffffff, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Lato Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}
/*a*/
.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #313131;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #ffffff;
    -fx-font-family: "Lato";
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #ebebeb;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: #ffffff;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #ffffff;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #ffffff;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #ffffff;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#ffffff, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(grey, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#ffffff, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #f5f5f5 transparent #f5f5f5;
    -fx-background-insets: 0;
    -fx-border-color: #ffffff #ffffff #383838 #ffffff;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Lato Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #f5f5f5, transparent, #f5f5f5;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}


#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: #ff7675;
}

#tags .yellow {
    -fx-background-color: #ffeaa7;
    -fx-text-fill: black;
}

#tags .blue {
    -fx-text-fill: black;
    -fx-background-color: #48dbfb;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: #ffa502;
}

#tags .brown {
    -fx-text-fill: black;
    -fx-background-color: #D7ACAC;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: #55efc4;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: #fd79a8;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#tags .purple {
    -fx-text-fill: black;
    -fx-background-color: #a29bfe;
}

#importance {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#importance .label {
    -fx-background-color: #FFE761;
    -fx-text-fill: black;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 3;
    -fx-background-radius: 2;
    -fx-font-size: 13;
}
```
