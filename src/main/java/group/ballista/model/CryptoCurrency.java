package group.ballista.model;

import group.ballista.connector.PriceConnector;

import java.math.BigDecimal;

public class CryptoCurrency {
    private final String ticker;

    public CryptoCurrency(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getAssetPrice(final String currency) {
        return PriceConnector.getInstance().getCryptoAssetPrice(ticker, currency);
    }
}
