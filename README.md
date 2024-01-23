# tp0124

Thanks for looking at my submission!

Best regards,
Tony

## Dependencies

This uses the latest JDK 21, gradle, picocli (for the cli), and a test logger.  I believe Java record type is the newest dependency used here.  This likely will work back to JDK 14 with minimal changes but I haven't tested older versions.

## Building

This is a standard gradle project.  You can build with the usual gradle commands.  You can generate a binary for usual cli calls with:

```bash
(base) tony@Tonys-MacBook-Air tp0124 % ./gradlew clean build distZip distTar installDist

> Task :app:test

  checkout.AccountingTest

    ✔ testSkipWeekdays()
    ✔ testSkipWeekends()
    ✔ testSkipHolidays()
    ✔ testChargeEveryDay()
    ✔ testSkipHolidaysWeekday()
    ✔ testSkipHolidaysWeekend()
    ✔ testSkipHolidaysDoubled()

  checkout.AppTest

    ✔ testAppDefaults()

  checkout.CheckoutTest

    ✔ testExceptionsCode()
    ✔ testExceptionsDays()
    ✔ testValid()
    ✔ testExceptionsCheckout()
    ✔ testExceptionsPercent()

  checkout.RentalAgreementTest

    ✔ testRentalAgreement()

  checkout.ToStringTest

    ✔ testToString()
    ✔ testAsDollars()
    ✔ testAsPercent()

  checkout.holiday.NearestHolidayTest

    ✔ testJulyFourth()

  checkout.holiday.RelativeHolidayTest

    ✔ testLaborDay()
    ✔ testThanksgiving()

  checkout.tables.HolidaysTest

    ✔ testHolidays()

  checkout.tables.ToolTypesTest

    ✔ testToolTypes()

  checkout.tables.ToolsTest

    ✔ testTools()

  23 passing (805ms)


BUILD SUCCESSFUL in 3s
11 actionable tasks: 11 executed
```

## Demo

It's possible to run and pass in arguments with the gradle command:

```bash
./gradlew run --args="-h"
```

However, you can also reference the generated bin directly:

```bash
(base) tony@Tonys-MacBook-Air tp0124 % ./app/build/install/app/bin/app -h
Usage: checkout [-hV] [-c=<checkoutDate>] [-d=<numDays>] [-p=<percentDiscount>]
                [-t=<code>]
Generates the rental agreement.
  -c, --checkout=<checkoutDate>
                         Check out date, MM/DD/YYYY format
  -d, --days=<numDays>   Rental day count - The number of days for which the
                           customer wants to rent the tool. (e.g. 4 days)
  -h, --help             Show this help message and exit.
  -p, --percent=<percentDiscount>
                         Discount percent - As a whole number, 0-100 (e.g. 20 =
                           20%)
  -t, --tool=<code>      Tool code
  -V, --version          Print version information and exit.
```

I prefer the second approach with the generated binary, as it displays colorized output which is lost via gradle.

An example, checkout call:

```bash
(base) tony@Tonys-MacBook-Air tp0124 % ./app/build/install/app/bin/app -d 1000 -t JAKR -p 33 -c 07/01/34


Tool code: JAKR
Tool type: Jackhammer
Tool brand: Ridgid
Rental days: 1000
Check out date: 07/01/34
Due date: 03/27/37
Daily rental charge: $2.99
Charge days: 708
Pre-discount charge: $2,116.92
Discount percent: 33%
Discount amount: $698.58
Final charge: $1,418.34

```

We also have helpful error messages.  See what happens with a negative percent:

```bash
(base) tony@Tonys-MacBook-Air tp0124 % ./app/build/install/app/bin/app -d 1000 -t JAKR -p -1 -c 07/01/34
Invalid value for option 'Discount percent': '-1' is not an int in [0, 100].
Usage: checkout [-hV] [-c=<checkoutDate>] [-d=<numDays>] [-p=<percentDiscount>]
                [-t=<code>]
Generates the rental agreement.
  -c, --checkout=<checkoutDate>
                         Check out date, MM/DD/YYYY format
  -d, --days=<numDays>   Rental day count - The number of days for which the
                           customer wants to rent the tool. (e.g. 4 days)
  -h, --help             Show this help message and exit.
  -p, --percent=<percentDiscount>
                         Discount percent - As a whole number, 0-100 (e.g. 20 =
                           20%)
  -t, --tool=<code>      Tool code
  -V, --version          Print version information and exit.
```
