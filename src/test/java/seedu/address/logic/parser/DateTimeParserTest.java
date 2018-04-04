package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.junit.Test;

//@@author deborahlow97
public class DateTimeParserTest {

    private static final Optional<LocalDateTime> nonEmptyLocalDateTime;
    private static final Optional<LocalDateTime> emptyLocalDateTime;

    static {
        nonEmptyLocalDateTime = Optional.of(LocalDateTime
            .of(2018, Month.JANUARY, 1, 15, 0, 0));
        emptyLocalDateTime = Optional.empty();
    }
    @Test
    public void parse_validArgs_success() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("1/1/2018 3pm");
        LocalDateTime aLocalDateTime = LocalDateTime.of(2018, Month.JANUARY, 1, 15, 0,
                0);
        assertEquals(dateTimeParse, nonEmptyLocalDateTime);
    }

    @Test
    public void parse_invalidFormatArgs_failure() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("1/1/20183pm");
        assertNotEquals(dateTimeParse, nonEmptyLocalDateTime);
    }

    @Test
    public void parse_invalidArgs_returnsNull() {
        Optional<LocalDateTime> dateTimeParse = nattyDateAndTimeParser("!@!(KJEw");
        assertEquals(dateTimeParse, emptyLocalDateTime);
    }
}
