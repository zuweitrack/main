package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedGoal> goals;
    @XmlElement
    private List<XmlAdaptedCca> ccas;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedReminder> reminders;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        goals = new ArrayList<>();
        ccas = new ArrayList<>();
        tags = new ArrayList<>();
        reminders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        goals.addAll(src.getGoalList().stream().map(XmlAdaptedGoal::new).collect(Collectors.toList()));
        ccas.addAll(src.getCcaList().stream().map(XmlAdaptedCca::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        reminders.addAll(src.getReminderList().stream().map(XmlAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}or {@code XmlAdaptedCca} or {@code XmlAdaptedTag} or (@code XmlAdaptedReminder)
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedCca c : ccas) {
            addressBook.addCca(c.toModelType());
        }
        for (XmlAdaptedGoal g : goals) {
            addressBook.addGoal(g.toModelType());
        }
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            addressBook.addPerson(p.toModelType());
        }
        for (XmlAdaptedReminder r : reminders) {
            addressBook.addReminder(r.toModelType());
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        XmlSerializableAddressBook otherAb = (XmlSerializableAddressBook) other;

        return persons.equals(otherAb.persons) && ccas.equals(otherAb.ccas)
                && tags.equals(otherAb.tags) && reminders.equals(otherAb.reminders);
    }
}
