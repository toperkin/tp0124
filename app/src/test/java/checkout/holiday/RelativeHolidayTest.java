package checkout.holiday;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class RelativeHolidayTest {
    @Test
    public void testThanksgiving() {
        // The 4th Thursday in November
        final Relative thanksgiving = Relative.of(Month.NOVEMBER, DayOfWeek.THURSDAY, 4);

        assertFalse(thanksgiving.isHoliday(LocalDate.of(2023, 11, 24)));
        assertTrue(thanksgiving.isHoliday(LocalDate.of(2023, 11, 23)));

        assertFalse(thanksgiving.isHoliday(LocalDate.of(2024, 11, 27)));
        assertTrue(thanksgiving.isHoliday(LocalDate.of(2024, 11, 28)));
    }

    @Test
    public void testLaborDay() {
        // The 1st Monday in September
        final Relative laborDay = Relative.of(Month.SEPTEMBER, DayOfWeek.MONDAY, 1);

        assertFalse(laborDay.isHoliday(LocalDate.of(2023, 9, 5)));
        assertTrue(laborDay.isHoliday(LocalDate.of(2023, 9, 4)));

        assertFalse(laborDay.isHoliday(LocalDate.of(2024, 9, 1)));
        assertTrue(laborDay.isHoliday(LocalDate.of(2024, 9, 2)));
    }
}
