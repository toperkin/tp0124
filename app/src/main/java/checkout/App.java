/*
 * This is the main entry point to the application.
 */
package checkout;

import java.time.DayOfWeek;
import java.time.Month;

import checkout.holiday.Nearest;
import checkout.holiday.Relative;
import checkout.models.Tool;
import checkout.models.ToolType;
import checkout.tables.Holidays;
import checkout.tables.ToolTypes;
import checkout.tables.Tools;
import picocli.CommandLine;

public final class App {
    private static final ToolType LADDER = new ToolType("Ladder", 199, true, true, false);
    private static final ToolType CHAINSAW = new ToolType("Chainsaw", 149, true, false, true);
    private static final ToolType JACKHAMMER = new ToolType("Jackhammer", 299, true, false, false);
    private static final ToolTypes DEFAULT_TYPES = ToolTypes.from(LADDER, CHAINSAW, JACKHAMMER);
    private static final Tools DEFAULT_TOOLS = Tools.from(
        new Tool("CHNS", CHAINSAW.label(), "Stihl"),
        new Tool("LADW", LADDER.label(), "Werner"),
        new Tool("JAKD", JACKHAMMER.label(), "DeWalt"),
        new Tool("JAKR", JACKHAMMER.label(), "Ridgid"));
    private static final Holidays DEFAULT_HOLIDAYS = Holidays.from(
        Nearest.of(Month.JULY, 4),
        Relative.of(Month.SEPTEMBER, DayOfWeek.MONDAY, 1));

    private final ToolTypes types;
    private final Tools tools;
    private final Holidays holidays;

    // This would be the point to establish database connections.  Since this
    // is a demo project we'll populate with the supplied records.
    public App() {
        this(DEFAULT_TYPES, DEFAULT_TOOLS, DEFAULT_HOLIDAYS);
    }

    // For dependency injection
    public App(final ToolTypes types, final Tools tools, final Holidays holidays) {
        this.types = types;
        this.tools = tools;
        this.holidays = holidays;
    }

    public ToolTypes types() {
        return types;
    }

    public Tools tools() {
        return tools;
    }

    public Holidays holidays() {
        return holidays;
    }

    public static void main(final String[] args) {
        System.exit(new CommandLine(new Checkout(new App())).execute(args));
    }
}
