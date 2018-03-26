package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author deborahlow97
/**
 * Contains utility methods used for parsing DateTime in the various *Parser classes.
 */
public class DateTimeParser {

    private static boolean isRecurring;
    private static boolean isTimeInferred;
    /**
     * Parses user input String specified{@code args} into LocalDateTime objects
     *
     * @return Empty Optional if args could not be parsed
     * @Disclaimer : The parser used is a dependency called 'natty' developed by 'Joe Stelmach'
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
        isRecurring = dateGroup.isRecurring();
        isTimeInferred = dateGroup.isTimeInferred();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Receives two LocalDateTime and ensures that the specified {@code endDateTime} is always later in time than
     * specified {@code startDateTime}
     *
     * @return endDateTime that checks the above confirmation
     */
    public static LocalDateTime balanceStartAndEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime newEndDateTime = endDateTime;
        while (startDateTime.compareTo(newEndDateTime) >= 1) {
            newEndDateTime = newEndDateTime.plusDays(1);
        }
        return newEndDateTime;
    }

    public static boolean containsDateAndTime(String args) {
        return nattyDateAndTimeParser(args).isPresent();
    }

    public static boolean isRecurringDate() {
        return isRecurring;
    }

    public static boolean isTimeInferredInArgs() {
        return isTimeInferred;
    }
}
