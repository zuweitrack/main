package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author deborahlow97
/**
 * Contains utility methods used for parsing DateTime in the various *Parser classes.
 */
public class DateTimeParser {

    private static final int BEGIN_INDEX = 6;
    /**
     * Parses user input String specified{@code args} into LocalDateTime objects
     *
     * @return Empty Optional if args could not be parsed
     * @Disclaimer : The parser used is a NLP API called 'natty' developed by 'Joe Stelmach'
     */
    public static Optional<LocalDateTime> nattyDateAndTimeParser(String args) {
        if (args == null || args.isEmpty()) {
            return Optional.empty();
        }

        Parser parser = new Parser();
        List groups = parser.parse(args);

        //Cannot be parsed
        if (groups.size() <= 0) {
            return Optional.empty();
        }

        DateGroup dateGroup = (DateGroup) groups.get(0);
        if (dateGroup.getDates().size() < 0) {
            return Optional.empty();
        }

        Date date = dateGroup.getDates().get(0);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Receives a LocalDateTime and formats the {@code dateTime}
     *
     * @return a formatted dateTime in String
     */
    public static String properDateTimeFormat(LocalDateTime dateTime) {
        StringBuilder builder = new StringBuilder();
        int day = dateTime.getDayOfMonth();
        String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        builder.append("Date: ")
                .append(day)
                .append(" ")
                .append(month)
                .append(" ")
                .append(year)
                .append(",  Time: ")
                .append(String.format("%02d", hour))
                .append(":")
                .append(String.format("%02d", minute));
        return builder.toString();
    }

    public static LocalDateTime getLocalDateTimeFromProperDateTime(String properDateTimeString) {
        String trimmedArgs = properDateTimeString.trim();
        int size = trimmedArgs.length();
        String stringFormat = properDateTimeString.substring(BEGIN_INDEX, size);
        stringFormat = stringFormat.replace(", Time: ", "");
        return nattyDateAndTimeParser(stringFormat).get();
    }

    /**
     * Receives a LocalDateTime and formats the {@code dateTime}
     *
     * @return a formatted dateTime in String
     */
    public static String properReminderDateTimeFormat(LocalDateTime dateTime) {
        StringBuilder builder = new StringBuilder();
        int day = dateTime.getDayOfMonth();
        String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        builder.append(day)
                .append("/")
                .append(month)
                .append("/")
                .append(year)
                .append(" ")
                .append(String.format("%02d", hour))
                .append(":")
                .append(String.format("%02d", minute));
        return builder.toString();
    }

    public static boolean containsDateAndTime(String args) {
        return nattyDateAndTimeParser(args).isPresent();
    }

    public static LocalDateTime getLocalDateTimeFromString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime;
    }
}
