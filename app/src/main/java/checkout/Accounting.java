package checkout;

import java.time.DayOfWeek;
import java.time.LocalDate;

import checkout.models.ToolType;
import checkout.tables.Holidays;

public final class Accounting {
    // Class only has static methods.  Make the constructor
    // private so people don't use it.
    private Accounting() { }

    public static Integer chargeDays(final LocalDate start, final Integer numDays, final ToolType type, final Holidays holidays) {
        Integer chargeDays = 0;
        for (int i = 0; i < numDays; i++) {
            final LocalDate testDate = start.plusDays(i);
            final Boolean isHoliday = holidays.isHoliday(testDate);
            final Boolean isWeekend = isWeekend(testDate);
            final Boolean isWeekday = !isWeekend;

            if (isHoliday && type.shouldChargeHolidays()) {
                chargeDays++;

            } else if (isWeekend && !isHoliday && type.shouldChargeWeekends()) {
                chargeDays++;

            } else if (isWeekday && !isHoliday && type.shouldChargeWeekday()) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private static boolean isWeekend(final LocalDate day) {
        final DayOfWeek dayOfWeek = day.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
