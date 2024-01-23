package checkout.holiday;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class NearestHolidayTest {
    @Test
    public void testJulyFourth() {
        final Nearest independenceDay = Nearest.of(Month.JULY, 4);
        // In 2025, it's a Friday
        // In 2026, it's a Saturday
        // In 2027, it's a Sunday
        // In 2028, it's a Tuesday

        // Positive cases
        assertTrue(independenceDay.isHoliday(LocalDate.of(2024, 7, 4)));
        assertTrue(independenceDay.isHoliday(LocalDate.of(2025, 7, 4)));
        assertTrue(independenceDay.isHoliday(LocalDate.of(2026, 7, 3))); // 4th is Saturday, so day before is off.
        assertTrue(independenceDay.isHoliday(LocalDate.of(2027, 7, 5))); // 4th is Sunday, so day after is off.
        assertTrue(independenceDay.isHoliday(LocalDate.of(2028, 7, 4)));

        // Negative cases
        assertFalse(independenceDay.isHoliday(LocalDate.of(2024, 7, 3)));
        assertFalse(independenceDay.isHoliday(LocalDate.of(2025, 7, 5)));
        assertFalse(independenceDay.isHoliday(LocalDate.of(2026, 7, 4))); // 4th is Saturday, so day before is off.
        assertFalse(independenceDay.isHoliday(LocalDate.of(2027, 7, 4))); // 4th is Sunday, so day after is off.
        assertFalse(independenceDay.isHoliday(LocalDate.of(2028, 7, 6)));
    }
}

