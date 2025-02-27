package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {
    public static Map<String, String> parseCommand(String input) {
        Map<String, String> params = new HashMap<>();
        Pattern pattern = Pattern.compile("-+([^=\\s]+)=?([^-]*)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2).trim();
            params.put(key, value.isEmpty() ? null : value);
        }
        return params;
    }
}
