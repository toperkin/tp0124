package checkout.models;

public final record ToolType(String label, Integer centsPerDay, Boolean shouldChargeWeekday, Boolean shouldChargeWeekends, Boolean shouldChargeHolidays) { }
