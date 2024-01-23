package checkout.tables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import checkout.models.Tool;

public class ToolsTest {

    @Test
    public void testTools() {
        final Tool tool = new Tool("some-code", "some-type-id", "some-brand");
        final Tools tools = Tools.from(tool);

        final Tool t = tools.get("some-code");
        assertEquals(tool, t);

        assertThrows(IllegalArgumentException.class, () -> {
            tools.get("some-other-code");
        });
    }
}
