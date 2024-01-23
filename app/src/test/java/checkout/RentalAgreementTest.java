package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class RentalAgreementTest {
    @Test
    public void testRentalAgreement() {
        // Ladder charges everyday except holidays.  There are no holidays in this
        // 20 day window.  We expect 20 days billed, at 199 cents/day, with a 9 percent
        // discount. Use 9 percent to see the roundHalfUp in action
        final RentalAgreement ra = RentalAgreement.builder(new App())
            .code("LADW")
            .numDays(20)
            .percentDiscount(9)
            .checkoutDate("01/01/11")
            .build();

        assertEquals(ra.toolCode(), "LADW");
        assertEquals(ra.toolType(), "Ladder");
        assertEquals(ra.toolBrand(), "Werner");
        assertEquals(ra.numDays(), 20);
        assertEquals(ra.percentDiscount(), 9);
        assertEquals(ra.checkoutDate(), LocalDate.parse("01/01/11", DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals(ra.dueDate(), LocalDate.parse("01/21/11", DateTimeFormatter.ofPattern("MM/dd/yy")));
        assertEquals(ra.numChargeDays(), 20);
        assertEquals(ra.centsPerDay(), 199);
        assertEquals(ra.preDiscountChargeCents(), 3980); // = 199 * 20
        assertEquals(ra.discountCents(), 358); // 199 * 20 * 9 / 100 = 358.2 => half_round_up goes down = 358
        assertEquals(ra.postDiscountChargeCents(), 3622); // pre - discount = 3980 - 358 => 3622
    }
}
