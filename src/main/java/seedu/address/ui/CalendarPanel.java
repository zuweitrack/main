package seedu.address.ui;

import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AddressBookChangedEvent;
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

    @Subscribe
    private void handleNewReminderEvent(AddressBookChangedEvent event) {
        reminderList = event.data.getReminderList();
        Platform.runLater(this::updateCalendar);
    }

    /**
     * Updates the Calendar with Reminders that are already added
     */
    private void updateCalendar() {
        setDateAndTime();
        CalendarSource myCalendarSource = new CalendarSource("Reminders");
        Calendar calendar = new Calendar("Reminders");
        calendar.setStyle(Calendar.Style.getStyle(1));
        calendar.setLookAheadDuration(Duration.ofDays(365));
        myCalendarSource.getCalendars().add(calendar);
        for (Reminder reminder : reminderList) {
            LocalDateTime ldtstart = nattyDateAndTimeParser(reminder.getDateTime().toString()).get();
            LocalDateTime ldtend = nattyDateAndTimeParser(reminder.getEndDateTime().toString()).get();
            calendar.addEntry(new Entry(reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
        }
        calendarView.getCalendarSources().add(myCalendarSource);
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
