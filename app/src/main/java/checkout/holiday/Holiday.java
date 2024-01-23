package checkout.holiday;

import java.time.LocalDate;

public interface Holiday {
    Boolean isHoliday(LocalDate date);
}
