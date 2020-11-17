package group.ballista.connector;

import group.ballista.util.ResponseParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Logger;

import static group.ballista.model.Constants.*;

public class PriceConnector {

    private final static Logger LOGGER = Logger.getLogger(PriceConnector.class.getName());

    private static PriceConnector INSTANCE;

    private final ResponseParser responseParser;

    private PriceConnector(){
        this.responseParser = new ResponseParser();
    }

    public static PriceConnector getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PriceConnector();
        }

        return INSTANCE;
    }

    public BigDecimal getCryptoAssetPrice(final String asset, final String displayCurrency) {
        try {
            return tryToGetCryptoPrice(asset, displayCurrency);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.severe(String.format(FAILED_MESSAGE_SERVER_ERROR, asset, e.getMessage()));
        }
        return new BigDecimal(-1);
    }

    private BigDecimal tryToGetCryptoPrice(final String ticker, String currency) throws
            URISyntaxException,
            IOException,
            InterruptedException {
        if (currency == null || currency.isBlank()) {
            currency = DEFAULT_CURRENCY;
        }

        if (ticker != null) {
            final URI uri = new URI(String.format(PRICE_ENDPOINT_PATTERN, ticker, currency));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            final HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                final Map<String, String> jsonContent = responseParser.contentFromJson(response.body());
                if (jsonContent.containsKey(currency.toUpperCase())) {
                    final String priceString = jsonContent.get(currency.toUpperCase());
                    return new BigDecimal(priceString);
                } else {
                    logErrorMessage(jsonContent, ticker, currency);
                    return new BigDecimal(-1);
                }
            } else {
                LOGGER.severe(String.format(FAILED_MESSAGE_SERVER_ERROR, ticker, response.body()));
                return new BigDecimal(-1);
            }
        } else {
            LOGGER.severe(FAILED_MESSAGE_NULL_PARAMS);
            return new BigDecimal(-1);
        }
    }

    private void logErrorMessage(final Map<String, String> jsonContent, final String ticker, final String currency) {
        if (jsonContent.containsKey(PARAM_WITH_ERROR_KEY)) {
            final String paramWithError = jsonContent.get(PARAM_WITH_ERROR_KEY);
            if (paramWithError.equals(CURRENCY_SYMBOL)) {
                LOGGER.severe(String.format(FAILED_MESSAGE_CURRENCY_ISSUE, ticker, currency));
            } else if (paramWithError.equals(TICKER_SYMBOL)) {
                LOGGER.severe(String.format(FAILED_MESSAGE_TICKER_ISSUE, ticker));
            } else {
                LOGGER.severe(String.format(FAILED_MESSAGE_GENERAL_ISSUE, ticker, currency));
            }
        } else {
            LOGGER.severe(String.format(FAILED_MESSAGE_GENERAL_ISSUE, ticker, currency));
        }
    }
}
