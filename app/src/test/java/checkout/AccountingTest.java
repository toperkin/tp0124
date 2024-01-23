package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import checkout.holiday.Nearest;
import checkout.models.ToolType;
import checkout.tables.Holidays;

public class AccountingTest {

    @Test
    public void testChargeEveryDay() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));
        final ToolType type = new ToolType("some-label", 100, true, true, true);
        final Holidays holidays = new App().holidays();

        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 10);
    }

    @Test
    public void testSkipWeekends() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, true, false, true);
        final Holidays holidays = new App().holidays();


        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 6);
    }

    @Test
    public void testSkipWeekdays() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, false, true, true);
        final Holidays holidays = new App().holidays();


        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 4);
    }

    @Test
    public void testSkipHolidays() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, true, true, false);
        final Holidays holidays = Holidays.from(
            Nearest.of(Month.JANUARY, 4), // Tues
            Nearest.of(Month.JANUARY, 5)  // Wed
        );

        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 8);
    }

    @Test
    public void testSkipHolidaysDoubled() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, true, true, false);
        final Holidays holidays = Holidays.from(
            Nearest.of(Month.JANUARY, 2), // Sun => Rounds to Mon
            Nearest.of(Month.JANUARY, 3)  // Mon
        );

        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 9);
    }

    @Test
    public void testSkipHolidaysWeekday() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, false, true, false);
        final Holidays holidays = Holidays.from(
            Nearest.of(Month.JANUARY, 2) // Sun => Rounds to Mon
        );

        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 4);
    }

    @Test
    public void testSkipHolidaysWeekend() {
        // SATURDAY
        final LocalDate date = LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy"));

        final ToolType type = new ToolType("some-label", 100, true, false, false);
        final Holidays holidays = Holidays.from(
            Nearest.of(Month.JANUARY, 2) // Sun => Rounds to Mon
        );

        assertEquals(Accounting.chargeDays(date, 10, type, holidays), 5);
    }
}
