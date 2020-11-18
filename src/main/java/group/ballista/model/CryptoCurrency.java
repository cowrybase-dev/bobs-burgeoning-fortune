package group.ballista.model;

import group.ballista.connector.PriceConnector;

import java.math.BigDecimal;

/**
 * This object represents a cryptocurrency
 */
public class CryptoCurrency {
    private final String ticker;

    public CryptoCurrency(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    /**
     * Gets the current price of the asset
     * @param currency
     * @return current price
     */
    public BigDecimal getAssetPrice(final String currency) {
        return PriceConnector.getInstance().getCryptoAssetPrice(ticker, currency);
    }
}
