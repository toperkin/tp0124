package checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.ParameterException;

public class CheckoutTest {
    @Test
    public void testValid() throws Exception {
        final List<String> msgs = new ArrayList<>();
        final CommandLine cl =  create(msgs);

        cl.execute("--tool", "JAKR", "--days", "1", "--percent", "13", "--checkout", "11/11/11");
        assertEquals(msgs.size(), 0);
    }

    @Test
    public void testExceptionsCode() throws Exception {
        final List<String> msgs = new ArrayList<>();
        final CommandLine cl =  create(msgs);

        cl.execute("--tool", "JAKRIIII", "--days", "1", "--percent", "13", "--checkout", "11/11/11");
        assertEquals(msgs.size(), 1);
        assertEquals(msgs.get(0), "Invalid value for option 'Tool Code': 'JAKRIIII' is Invalid");
    }

    @Test
    public void testExceptionsDays() throws Exception {
        final List<String> msgs = new ArrayList<>();
        final CommandLine cl =  create(msgs);

        cl.execute("--tool", "JAKR", "--days", "0", "--percent", "13", "--checkout", "11/11/11");
        assertEquals(msgs.size(), 1);
        assertEquals(msgs.get(0), "Invalid value for option 'Rental day count': '0' is not a positive integer.");
    }

    @Test
    public void testExceptionsPercent() throws Exception {
        final List<String> msgs = new ArrayList<>();
        final CommandLine cl =  create(msgs);

        cl.execute("--tool", "JAKR", "--days", "1", "--percent", "-13", "--checkout", "11/11/11");
        assertEquals(msgs.size(), 1);
        assertEquals(msgs.get(0), "Invalid value for option 'Discount percent': '-13' is not an int in [0, 100].");

    }

    @Test
    public void testExceptionsCheckout() throws Exception {
        final List<String> msgs = new ArrayList<>();
        final CommandLine cl =  create(msgs);

        cl.execute("--tool", "JAKR", "--days", "1", "--percent", "13", "--checkout", "11/1/11");
        assertEquals(msgs.size(), 1);
        assertEquals(msgs.get(0), "Invalid value for option 'Check out date': '11/1/11' is not a date like MM/dd/yy.");
    }

    private CommandLine create(final List<String> msgs) {
        final CommandLine cl = new CommandLine(new Checkout(new App()));
        final IParameterExceptionHandler parameterExceptionHandlerHandler = new IParameterExceptionHandler() {
            public int handleParseException(final ParameterException ex, final String[] args) throws Exception {
                msgs.add(ex.getMessage());
                return 0;
            }
        };
        cl.setParameterExceptionHandler(parameterExceptionHandlerHandler);
        return cl;
    }
}
