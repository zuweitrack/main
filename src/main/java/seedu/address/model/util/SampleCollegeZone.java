package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getSamplePersons;
import static seedu.address.model.util.SampleGoalDataUtil.getSampleGoals;
import static seedu.address.model.util.SampleReminderDataUtil.getSampleReminders;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.goal.exceptions.DuplicateGoalException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;


//@@author deborahlow97
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
