package group.ballista.model;

public interface Constants {
    String PRICE_ENDPOINT_PATTERN = "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=%s";
    String DEFAULT_CURRENCY = "EUR";
    String EMPTY_STRING = "";
    String QUOTES_REGEX = "^\"|\"$";
    String COLON_STRING = ":";
    String COMMA_STRING = ",";
    String OPENING_BRACKET = "{";
    String CLOSING_BRACKET = "}";

    String CURRENCY_SYMBOL = "tsyms";
    String TICKER_SYMBOL = "fsym";
    String PARAM_WITH_ERROR_KEY = "ParamWithError";
    String FAILED_MESSAGE_TICKER_ISSUE = "Failed to get price of asset %s. Please provide the right asset";
    String FAILED_MESSAGE_CURRENCY_ISSUE = "Failed to get %s price with currency %s. Please provide right currency";
    String FAILED_MESSAGE_GENERAL_ISSUE = "Failed to get price of %s with currency %s. Please double check the parameters and try again";
    String FAILED_MESSAGE_SERVER_ERROR = "Failed to get %s price. Error: %s";
    String FAILED_MESSAGE_NULL_PARAMS = "Crypto asset can not be null";
}
