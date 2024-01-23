package checkout.tables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import checkout.holiday.Holiday;

public final class Holidays {
    private final List<Holiday> holidayList = new ArrayList<>();

    public static Holidays from(final Holiday... holidays) {
        final Holidays h = new Holidays();
        for (Holiday holiday : holidays) {
            h.add(holiday);
        }
        return h;
    }

    public Holidays add(final Holiday holiday) {
        holidayList.add(holiday);
        return this;
    }

    public boolean isHoliday(final LocalDate date) {
        // This is assuming the holiday's list is small.  Say 30ish.  If
        // we decide to have 10k holidays in a year, we can improve this logic in a few ways.
        // 1.  At start up, generate a map {date => isHoliday}, then this just becomes a lookup.
        // 2.  Dedupe holidays going into the list.  If there's already a holiday recorded for a
        //     particular day, then we can drop any that are repeats.
        return holidayList.stream().anyMatch(h -> h.isHoliday(date));
    }
}
