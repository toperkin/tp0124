package checkout.tables;

import java.util.HashMap;
import java.util.Map;

import checkout.models.ToolType;


public final class ToolTypes {

    private final Map<String, ToolType> tt = new HashMap<>();

    public static ToolTypes from(final ToolType... types) {
        final ToolTypes toolTypes = new ToolTypes();
        for (ToolType type : types) {
            toolTypes.put(type);
        }
        return toolTypes;
    }

    public ToolTypes put(final ToolType type) {
        tt.put(type.label(), type);
        return this;
    }

    public ToolType get(final String type) {
        if (tt.containsKey(type)) {
            return tt.get(type);
        } else {
            throw new IllegalArgumentException(String.format(
                "Invalid value for option 'Tool type': '%s' is Invalid", type));
        }
    }
}
