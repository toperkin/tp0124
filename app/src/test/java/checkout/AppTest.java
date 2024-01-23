package checkout;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    public void testAppDefaults() {
        final App app = new App();
        assertNotNull(app.tools());
        assertNotNull(app.types());
        assertNotNull(app.holidays());
    }
}
