package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import com.calendarfx.model.Calendar;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
//import com.calendarfx.model.CalendarEvent;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.ui.PersonListPanel.*;
import seedu.address.model.person.Person;


/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarView;

    public CalendarPanel(ObservableList<Person> personList) {
        super(FXML);

        Person person = personList.get(1);

        String meetDate = person.getMeetDate().value;

        System.out.println(meetDate);

        calendarView = new CalendarView();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
        Calendar holidays = new Calendar("Holidays");
        ZonedDateTime zdt = ZonedDateTime.parse("2018-04-16T10:15:30+01:00[Europe/Paris]");


        calendarView.createEntryAt(zdt, holidays);


        holidays.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(holidays);

        calendarView.getCalendarSources().addAll(myCalendarSource);
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

}
