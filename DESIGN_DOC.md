# Point of sale checkout system design notes

The functional specification outlines many requirements of this system.

The objective of this document is to highlight some key points of interest in regards to the design, architecture, and implementation.

Functional specification: *redacted*

## Code organization

Let's organize the core logic around three main parts:

* **Checkout.java** - The endpoints to the application.  Serves the checkout API.
* **RentalAgreement.java** - Takes the raw data received from a Checkout request and formats it into the RentalAgreement structure.  Includes associated serialization methods.
* **App.java** - The main entry point of the application.  This is responsible for initializing resource connections; databases + checkout service.

This generally supports the [Single responsibility principle
](https://en.wikipedia.org/wiki/Single_responsibility_principle), which as a side effect allows for isolated testing via direct dependency injection.

Feel free to include specialized classes with static methods for calculations and formatting.  Models and DB interface classes are also encouraged.

## Data

We'll need to add some persistent storage for the following data types.

...note: Design note

We don't expect these data models to change rapidly.  Add a cache with 1 day TTL to persist this locally.

### ToolType

Many tools share a common type.  Billing will be consistent across types of the same tool.

...note: Data model

We'll want a database to track all the tool types.  It should have the following fields:

* String, label: the general category of the tool.  This is a unique identifier of the type, and we'll use it as the primary key.
* Int, centsPerDay: the bill rate per day in cents.  We don't want to do floating point and rounding with money.  This also uses a more conservative type for storage than floats.
* Boolean, shouldChargeWeekday: if the item is to be billed per weekday usage.
* Boolean, shouldChargeWeekends: if the item is to be billed per weekend usage.
* Boolean, shouldChargeHolidays: if the item is to be billed per holiday usage.

### Tool

This is a unique record of a specific tool.

...note: Data model

We'll want a database to track all the tools.  It should have the following fields:

* String, code: a unique identifier assigned to this tool.  We use simple codes, for easy reference by the customer and store techs.
* String, toolTypeId: the ID reference of the type corresponding to this tool.
* String, brand: The name of the brand.

### Holiday

Holidays are complicated. There are a two types will need to consider at this point.

* *Nearest*: Holidays which occur on the nearest weekday.  For example, July 4th 2026 is a Saturday, and will be observed on the Friday prior.

* *Relative*: Holidays which occur on a relative date.  Ex.  Labor day is the first Monday in September.

...note: Data model

We'll want a single database to track all the holidays.  It should have the following fields:

* String, name: a display name of the holiday
* Enum, type: "nearest" or "relative"
* Enum, month: month the holiday is in reference to
* Int, dayOfMonth: Required for `nearest`, ignored otherwise
* Enum, dayOfWeek: Required for `relative`, ignored otherwise
* Int, occurrence: Required for `relative`, ignored otherwise.  Used indicate if the **nth** of such day in the month is the holiday.

There shouldn't be very many holidays in a year.  Otherwise we'd never working.  We'll want to keep and eye out on use-case creep for this table.  If it starts to add columns or gets really big, we may need to break it up into tables by holiday type.

...note: Implementation details

1. The concept of Holiday is a good candidate for **interface**.  We want both Nearest and Relative holidays to implement a method to determine if a day is that holiday, e.g. `isHoliday(date)`.  This way we can have a single list of holidays later which uses either type interchangeably.  Later we can add logic for new holiday types, e.g. lunar, without complicating the existing logic.
2. Older versions of the JDK didn't have great support for holiday calculation.  Be wary of old stack overflow answers.  Since JDK version 8, `java.time.LocalDate` should have everything you need.
3. The project specification is ambiguous on some edge cases for billing.  For example, if a product bills on the weekend but not on holidays, and that weekend day happens to be a holiday... Is it free because it's a holiday, or does it cost because it's a weekend?  Let's formalized this, so holiday comes first.
   1. If the day is a holiday, use the "should charge on holiday" property (regardless of the other property values).
   2. If the day is a weekend and not a holiday, use the "should charge the weekend" property.
   3. If the day is a weekday and not a holiday, use the "should charge the weekday" property.

