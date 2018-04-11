package seedu.address.model.goal;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author deborahlow97
public class StartDateTimeTest {

    private final StartDateTime startDateTimeObjectOne = new StartDateTime("Date: 18 April 2018, Time: 20:20");
    private final StartDateTime startDateTimeObjectTwo = new StartDateTime("Date: 17 April 2018, Time: 20:20");
    private final StartDateTime startDateTimeObjectThree = new StartDateTime("Date: 17 April 2018, Time: 20:20");


    @Test
    public void startDateTimeCompareTo_testGreaterThan_success() {
        int result = startDateTimeObjectOne.compareTo(startDateTimeObjectTwo);
        assertTrue(result == 1);
    }

    @Test
    public void startDateTimeCompareTo_testLessThan_success() {
        int result = startDateTimeObjectThree.compareTo(startDateTimeObjectOne);
        assertTrue(result == -1);
    }
}
