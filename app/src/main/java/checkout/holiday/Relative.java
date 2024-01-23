package checkout.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public final class Relative implements Holiday {
    private final Month month;
    private final DayOfWeek dayOfWeek;
    private final Integer occurrence;

    private Relative(final Month month, final DayOfWeek dayOfWeek, final Integer occurrence) {
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.occurrence = occurrence;
    }

    public static Relative of(final Month month, final DayOfWeek dayOfWeek, final Integer occurrence) {
        return new Relative(month, dayOfWeek, occurrence);
    }

    public Boolean isHoliday(final LocalDate date) {
        final LocalDate holiday = YearMonth.of(date.getYear(), month)
            .atDay(1)
            .with(TemporalAdjusters.dayOfWeekInMonth(occurrence, dayOfWeek));

        return holiday.equals(date);
    }
}
