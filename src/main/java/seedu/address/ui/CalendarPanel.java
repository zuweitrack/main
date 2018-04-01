package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.Reminder;

//@@author fuadsahmawi
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    
    private CalendarView calendarView;
    
    private ObservableList<Reminder> reminderList;

    public CalendarPanel(ObservableList<Reminder> reminderList) {
        super(FXML);
        
        this.reminderList = reminderList;

        calendarView = new CalendarView();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
        updateCalendar();
        registerAsAnEventHandler(this);
    }
    
    private void updateCalendar() {
        setDateAndTime();
        CalendarSource myCalendarSource = new CalendarSource("Reminders");
        int styleNum = 0;
        for (Reminder reminder : reminderList) {
            Calendar calendar = getCalendar(styleNum, reminder);
            myCalendarSource.getCalendars().add(calendar);
            styleNum++;
            styleNum = styleNum % 5;
            Parser parser = new Parser();
            Date reminderDateTime = null;
            List<DateGroup> groups = parser.parse(reminder.getDateTime().toString());
            for (DateGroup group : groups) {
                reminderDateTime = group.getRecursUntil();
            }
            LocalDateTime ldtstart = LocalDateTime.ofInstant(reminderDateTime.toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime ldtend = LocalDateTime.ofInstant(reminderDateTime.toInstant(),
                    ZoneId.systemDefault());
            calendar.addEntry(new Entry(reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
        }
        calendarView.getCalendarSources().add(myCalendarSource);
    }
    
    private Calendar getCalendar(int styleNum, Reminder reminder) {
        Calendar calendar = new Calendar(reminder.getReminderText().toString());
        calendar.setStyle(Calendar.Style.getStyle(styleNum));
        calendar.setLookAheadDuration(Duration.ofDays(365));
        return calendar;
    }
    
    
    private void setDateAndTime() {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }
}
