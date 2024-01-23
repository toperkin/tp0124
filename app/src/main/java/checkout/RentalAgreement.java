package checkout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import checkout.models.Tool;
import checkout.models.ToolType;

public final class RentalAgreement {
    private static final String PATTERN = "MM/dd/yy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private final String toolCode;
    private final String toolType;
    private final String toolBrand;
    private final Integer numDays;
    private final Integer percentDiscount;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final Integer numChargeDays;
    private final Integer centsPerDay;
    private final Integer preDiscountChargeCents;
    private final Integer postDiscountChargeCents;
    private final Integer discountCents;

    private RentalAgreement(final Builder b) {
        this.toolCode = b.toolCode;
        this.toolType = b.toolType;
        this.toolBrand = b.toolBrand;
        this.centsPerDay = b.centsPerDay;
        this.numDays = b.numDays;
        this.percentDiscount = b.percentDiscount;
        this.checkoutDate = b.checkoutDate;
        this.dueDate = b.dueDate;
        this.numChargeDays = b.numChargeDays;
        this.preDiscountChargeCents = b.preDiscountChargeCents;
        this.postDiscountChargeCents = b.postDiscountChargeCents;
        this.discountCents = b.discountCents;
    }

    public String toolCode() {
        return toolCode;
    }

    public String toolType() {
        return toolType;
    }

    public String toolBrand() {
        return toolBrand;
    }

    public Integer numDays() {
        return numDays;
    }

    public Integer percentDiscount() {
        return percentDiscount;
    }

    public LocalDate checkoutDate() {
        return checkoutDate;
    }

    public LocalDate dueDate() {
        return dueDate;
    }

    public Integer numChargeDays() {
        return numChargeDays;
    }

    public Integer centsPerDay() {
        return centsPerDay;
    }

    public Integer preDiscountChargeCents() {
        return preDiscountChargeCents;
    }

    public Integer postDiscountChargeCents() {
        return postDiscountChargeCents;
    }

    public Integer discountCents() {
        return discountCents;
    }

    public String toString() {
        return ToString.builder()
            .add("Tool code", toolCode)
            .add("Tool type", toolType)
            .add("Tool brand", toolBrand)
            .add("Rental days", numDays.toString())
            .add("Check out date", checkoutDate.format(FORMATTER))
            .add("Due date", dueDate.format(FORMATTER))
            .add("Daily rental charge", ToString.asDollars(centsPerDay))
            .add("Charge days", numChargeDays.toString())
            .add("Pre-discount charge", ToString.asDollars(preDiscountChargeCents))
            .add("Discount percent", ToString.asPercent(percentDiscount))
            .add("Discount amount", ToString.asDollars(discountCents))
            .add("Final charge", ToString.asDollars(postDiscountChargeCents))
            .build();
    }

    private static Integer roundHalfUp(final Double d) {
        return (int) Math.floor(d + 0.5);
    }

    public static Builder builder(final App appConfig) {
        return new Builder(appConfig);
    }

    public static final class Builder {
        private final App appConfig;

        private String code;
        private String checkoutDateStr;

        // Derived fields
        private String toolCode;
        private String toolType;
        private String toolBrand;
        private Integer numDays;
        private Integer percentDiscount;
        private LocalDate checkoutDate;
        private LocalDate dueDate;
        private Integer numChargeDays;
        private Integer centsPerDay;
        private Integer preDiscountChargeCents;
        private Integer postDiscountChargeCents;
        private Integer discountCents;

        public Builder(final App appConfig) {
            this.appConfig = appConfig;
        }

        public Builder code(final String code) {
            this.code = code;
            return this;
        }

        public Builder numDays(final Integer numDays) {
            this.numDays = numDays;
            return this;
        }

        public Builder percentDiscount(final Integer percentDiscount) {
            this.percentDiscount = percentDiscount;
            return this;
        }

        public Builder checkoutDate(final String checkoutDateStr) {
            this.checkoutDateStr = checkoutDateStr;
            return this;
        }

        public RentalAgreement build() {
            final Tool tool = appConfig.tools().get(code);
            final ToolType type = appConfig.types().get(tool.typeId());

            if (numDays == null || numDays < 1) {
                throw new IllegalArgumentException(
                    String.format("Invalid value for option 'Rental day count': '%s' is not a positive integer.", numDays));
            }

            if (percentDiscount == null || percentDiscount < 0 || percentDiscount > 100) {
                throw new IllegalArgumentException(
                    String.format("Invalid value for option 'Discount percent': '%s' is not an int in [0, 100].", percentDiscount));
            }

            if (checkoutDateStr == null) {
                throw new IllegalArgumentException(
                    String.format("Invalid value for option 'Check out date': '%s' is not a date like %s.", checkoutDateStr, PATTERN));
            }

            try {
                this.checkoutDate = LocalDate.parse(checkoutDateStr, FORMATTER);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    String.format("Invalid value for option 'Check out date': '%s' is not a date like %s.", checkoutDateStr, PATTERN));
            }

            this.toolCode = tool.code();
            this.toolType = type.label();
            this.toolBrand = tool.brand();
            this.centsPerDay = type.centsPerDay();
            this.dueDate = checkoutDate.plusDays(numDays);
            this.numChargeDays = Accounting.chargeDays(checkoutDate, numDays, type, appConfig.holidays());
            this.preDiscountChargeCents = type.centsPerDay() * numChargeDays;
            this.discountCents = roundHalfUp(percentDiscount * preDiscountChargeCents / 100.0);
            this.postDiscountChargeCents = preDiscountChargeCents - discountCents;

            return new RentalAgreement(this);
        }
    }
}
