package group.ballista.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test ResponseParser")
class ResponseParserTest {

    private final ResponseParser responseParser = new ResponseParser();

    @Test
    @DisplayName("Response parser should successfully parse json response")
    void testParseSuccess() {
        final String response = "{\"USD\":16838.99}";
        final Map<String, String> contentFromJson = responseParser.contentFromJson(response);
        assertTrue(contentFromJson.size() == 1);
    }

    @Test
    @DisplayName("Response parser should successfully parse wrong params error response leaving out empty json " +
            "elements")
    void testParseErrorWrongParam() {
        final String response = "{\"Response\":\"Error\",\"Message\":\"tsyms param seems to be missing.\"," +
                "\"HasWarning\":false,\"Type\":2,\"RateLimit\":{},\"Data\":{},\"ParamWithError\":\"tsyms\"}";
        final Map<String, String> contentFromJson = responseParser.contentFromJson(response);
        assertTrue(contentFromJson.size() == 5);
    }

    @Test
    @DisplayName("Response parser should successfully parse missing params error response leaving out empty json " +
            "elements")
    void testParseErrorMissingParam() {
        final String response = "{\"Response\":\"Error\",\"Message\":\"There is no data for any of the toSymbols USSD" +
                " .\",\"HasWarning\":true,\"Type\":2,\"RateLimit\":{},\"Data\":{},\"Warning\":\"There is no data for " +
                "the toSymbol/s USSD \",\"ParamWithError\":\"tsyms\"}";
        final Map<String, String> contentFromJson = responseParser.contentFromJson(response);
        assertTrue(contentFromJson.size() == 6);
    }

    @Test
    @DisplayName("Response parser should return empty map when json is empty")
    void testParseEmptyJson() {
        final String response = "{}";
        final Map<String, String> contentFromJson = responseParser.contentFromJson(response);
        assertTrue(contentFromJson.isEmpty());
    }
}
