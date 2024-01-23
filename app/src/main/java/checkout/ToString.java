package checkout;

public final class ToString {
    private final StringBuilder result;

    private ToString() {
        this.result = new StringBuilder();
    }

    public static ToString builder() {
        return new ToString();
    }

    public ToString add(final String fieldName, final String fieldValue) {
        result.append(fieldName).append(": ").append(fieldValue).append("\n");
        return this;
    }

    public String build() {
        return result.toString();
    }

    // Static formatters
    public static String asDollars(final Integer cents) {
        final Double dollars = cents / 100.0;
        return String.format("$%,.2f", dollars);
    }

    public static String asPercent(final Integer percent) {
        return String.format("%s%%", percent);
    }
}
