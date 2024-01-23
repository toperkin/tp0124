package checkout.tables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import checkout.models.ToolType;

public class ToolTypesTest {

    @Test
    public void testToolTypes() {
        final ToolType type = new ToolType("some-label", 199, true, true, false);
        final ToolTypes types = ToolTypes.from(type);

        final ToolType t = types.get("some-label");
        assertEquals(type, t);

        assertThrows(IllegalArgumentException.class, () -> {
            types.get("some-other-label");
        });
    }
}
