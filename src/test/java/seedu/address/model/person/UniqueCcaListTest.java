package seedu.address.model.person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author deborahlow97
public class UniqueCcaListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCcaList uniqueCcaList = new UniqueCcaList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCcaList.asObservableList().remove(0);
    }
}

