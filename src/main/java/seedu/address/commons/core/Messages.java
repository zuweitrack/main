package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!";

    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Start Date cannot be later than End Date or Start/End Date cannot be earlier than current date! \n%1$s";

    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_GOAL_DISPLAYED_INDEX = "The goal index provided is invalid";
    public static final String MESSAGE_INVALID_REMINDER_TEXT_DATE = "The reminder text or start date "
            + "provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_RA_LISTED_OVERVIEW = "%1$d Searched Persons and "
            + "their Resident Assistant(s) (RA) listed!";
    public static final String MESSAGE_INVALID_SORT_COMMAND_USAGE = "Sort command cannot be done on an empty "
            + "goal list!";
    public static final String MESSAGE_GOAL_ONGOING_ERROR = "Goal is already ongoing.";
    public static final String MESSAGE_GOAL_COMPLETED_ERROR = "Goal is already completed.";

}
