package parser;

import java.util.HashMap;
import java.util.Map;

public class StringParser {
    public static Map<String, String> parseCommand(String input) {
        Map<String, String> params = new HashMap<>();
        String[] parts = input.split(" ");

        for (String part : parts) {
            if (part.startsWith("-")) {
                String[] keyValue = part.replaceFirst("^-+", "")
                        .split("=");
                String key = keyValue[0];
                String value = (keyValue.length > 1) ? keyValue[1] : null;
                params.put(key, value);
            }
        }
        return params;
    }
}
