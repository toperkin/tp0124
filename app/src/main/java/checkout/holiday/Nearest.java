package checkout.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public final class Nearest implements Holiday {
    private final Month month;
    private final Integer dayOfMonth;

    private Nearest(final Month month, final Integer dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public static Nearest of(final Month month, final Integer dayOfMonth) {
        return new Nearest(month, dayOfMonth);
    }

    private static LocalDate referenceDate(final int year, final Month month, final int dayOfMonth) {
        final LocalDate h = LocalDate.of(year, month, dayOfMonth);

        if (h.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return h.minusDays(1);
        }

        if (h.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return h.plusDays(1);
        }

        return h;
    }

    public Boolean isHoliday(final LocalDate date) {
        final LocalDate ref = referenceDate(date.getYear(), month, dayOfMonth);
        return ref.equals(date);
    }
}
