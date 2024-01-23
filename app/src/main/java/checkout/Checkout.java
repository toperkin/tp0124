package checkout;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@Command(name = "checkout", mixinStandardHelpOptions = true, version = "checkout 1.0",
         description = "Generates the rental agreement.")
public final class Checkout implements Callable<Integer> {
    private final App appConfig;

    public Checkout(final App appConfig) {
        this.appConfig = appConfig;
    }

    @Spec
    private CommandSpec spec;

    @Option(names = {"-t", "--tool"}, description = "Tool code")
    private String code;

    @Option(names = {"-d", "--days"}, description = "Rental day count - The number of days for which the customer wants to rent the tool. (e.g. 4 days)")
    private Integer numDays;

    @Option(names = {"-p", "--percent"}, description = "Discount percent - As a whole number, 0-100 (e.g. 20 = 20%%)")
    private Integer percentDiscount;

    @Option(names = {"-c", "--checkout"}, description = "Check out date, MM/DD/YYYY format")
    private String checkoutDate;

    @Override
    public Integer call() throws Exception {
        final RentalAgreement rentalAgreement;
        try {
            rentalAgreement = RentalAgreement.builder(appConfig)
                .code(code)
                .numDays(numDays)
                .percentDiscount(percentDiscount)
                .checkoutDate(checkoutDate)
                .build();
        } catch (IllegalArgumentException e) {
            throw new ParameterException(spec.commandLine(), e.getMessage());
        }

        System.out.println("\n\n");
        System.out.println(rentalAgreement);
        System.out.println("\n\n");
        return 0;
    }
}
