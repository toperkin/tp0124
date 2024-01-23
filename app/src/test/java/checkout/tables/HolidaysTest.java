package checkout.tables;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import checkout.holiday.Nearest;
import checkout.holiday.Relative;

public class HolidaysTest {
    @Test
    public void testHolidays() {
        final Nearest independenceDay = Nearest.of(Month.JULY, 4);
        final Relative thanksgiving = Relative.of(Month.NOVEMBER, DayOfWeek.THURSDAY, 4);

        final Holidays holidays = Holidays.from(independenceDay, thanksgiving);

        // Either Thanksgiving for Independence Day should match
        assertTrue(holidays.isHoliday(LocalDate.of(2023, 11, 23)));
        assertTrue(holidays.isHoliday(LocalDate.of(2025, 7, 4)));

        // Neither
        assertFalse(holidays.isHoliday(LocalDate.of(2024, 02, 11)));
    }
}
