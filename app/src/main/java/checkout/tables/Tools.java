package checkout.tables;

import java.util.HashMap;
import java.util.Map;

import checkout.models.Tool;


public final class Tools {

    private final Map<String, Tool> tools = new HashMap<>();

    public static Tools from(final Tool... t) {
        final Tools tools = new Tools();
        for (Tool tool: t) {
            tools.put(tool);
        }
        return tools;
    }

    public Tools put(final Tool tool) {
        tools.put(tool.code(), tool);
        return this;
    }

    public Tool get(final String code) {
        if (tools.containsKey(code)) {
            return tools.get(code);
        } else {
            throw new IllegalArgumentException(String.format(
                "Invalid value for option 'Tool Code': '%s' is Invalid", code));
        }
    }
}
