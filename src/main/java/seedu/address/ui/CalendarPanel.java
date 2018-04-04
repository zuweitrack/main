package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.model.CalendarEvent;

import javafx.scene.layout.Region;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarView;

    public CalendarPanel() {
        super(FXML);

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
