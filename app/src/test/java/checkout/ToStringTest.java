package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToStringTest {
    @Test
    public void testToString() {
        final String output = ToString.builder()
            .add("a", "b")
            .add("cc", "d")
            .build();

        assertEquals(output, "a: b\ncc: d\n");
    }

    @Test
    public void testAsDollars() {
        assertEquals(ToString.asDollars(123456789), "$1,234,567.89");
    }

    @Test
    public void testAsPercent() {
        assertEquals(ToString.asPercent(12), "12%");
    }
}
