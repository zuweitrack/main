package seedu.address.ui;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

import javax.swing.plaf.synth.Region;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;


/**
 * The Calendar Panel of the App.
 */
public class CalendarMeetPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarMeetView;

    public CalendarMeetPanel(ObservableList<Person> personList) {
        super(FXML);

        Person person = personList.get(1);

        String meetDate = person.getMeetDate().value;

        System.out.println(meetDate);

        calendarMeetView = new CalendarView();
        calendarMeetView.setRequestedTime(LocalTime.now());
        calendarMeetView.setToday(LocalDate.now());
        calendarMeetView.setTime(LocalTime.now());
        calendarMeetView.setShowAddCalendarButton(false);
        calendarMeetView.setShowSearchField(false);
        calendarMeetView.setShowSearchResultsTray(false);
        calendarMeetView.setShowPrintButton(false);
        calendarMeetView.showMonthPage();
        Calendar holidays = new Calendar("MeetCalendar");
        ZonedDateTime zdt = ZonedDateTime.parse("2018-04-16T10:15:30+01:00[Europe/Paris]");

        calendarMeetView.createEntryAt(zdt, holidays);


        holidays.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(holidays);

        calendarMeetView.getCalendarSources().addAll(myCalendarSource);
    }

    public CalendarView getCalendarRoot() {
        return this.calendarMeetView;
    }
}
