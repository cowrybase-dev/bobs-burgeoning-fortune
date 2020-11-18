package group.ballista.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static group.ballista.model.Constants.*;

/**
 * Custom parser used to parse json response data
 */
public class ResponseParser {

    /**
     * Parses json output and returns a Map containing
     * keys and corresponding values
     * @param json string
     * @return map containing json elements
     */
    public Map<String, String> contentFromJson(final String json) {
        if (json != null && !json.isBlank()) {
            final Map<String, String> result = new HashMap<>();
            final String jsonWithoutBrackets = json.replace(OPENING_BRACKET, EMPTY_STRING).replace(CLOSING_BRACKET, EMPTY_STRING);
            final String[] jsonElements = jsonWithoutBrackets.split(COMMA_STRING);
            Arrays.stream(jsonElements).forEach(element -> result.putAll(entryFrom(element)));
            return result;
        }
        return Map.of();
    }

    private Map<String, String> entryFrom(final String jsonKeyValue) {
        if (jsonKeyValue != null && !jsonKeyValue.isBlank()) {
            final String[] keyValueArray = jsonKeyValue.split(COLON_STRING);
            if (keyValueArray.length == 2) {
                final String key = String.valueOf(keyValueArray[0]);
                final String value = String.valueOf(keyValueArray[1]);
                if ((key != null && !key.isBlank()) && (value != null && !value.isBlank())) {
                    return Map.of(key.replaceAll(QUOTES_REGEX, EMPTY_STRING), value.replaceAll(QUOTES_REGEX, EMPTY_STRING));
                }
            }
        }
        return Map.of();
    }
}
