package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Cca;

//@@author deborahlow97
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
