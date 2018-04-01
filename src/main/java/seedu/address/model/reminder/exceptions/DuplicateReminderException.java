package seedu.address.model.reminder.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author fuadsahmawi
/**
 * Signals that the operation will result in duplicate Goal objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation will result in duplicate reminders");
    }
}
