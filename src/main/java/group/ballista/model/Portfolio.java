package group.ballista.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static group.ballista.model.Constants.DEFAULT_CURRENCY;

public class Portfolio {
    private final List<PortfolioItem> cryptoCurrencies;

    private String currency;

    public Portfolio() {
        this.cryptoCurrencies = new ArrayList<>();
        this.currency = DEFAULT_CURRENCY;
    }

    public Portfolio(final String currency) {
        this.cryptoCurrencies = new ArrayList<>();
        this.currency = currency;
    }

    public void addAsset(final PortfolioItem item) {
        this.cryptoCurrencies.add(item);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPortfolioValue() {
        return cryptoCurrencies.stream()
                               .map(crypto -> crypto.getBagValue(getCurrency()))
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        if (this.cryptoCurrencies.isEmpty()) {
            return "Empty Portfolio";
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("====== Portfolio (in " + getCurrency() + ") ======\n\n");
        this.cryptoCurrencies.forEach(item -> {
            final CryptoCurrency asset = item.getCryptoCurrency();
            stringBuilder.append("Asset: " + asset.getTicker()
                    + "; Bag size: " + item.getBagSize().doubleValue()
                    + "; Unit price: " + asset.getAssetPrice(getCurrency()).doubleValue()
                    + "; Total value: " + item.getBagValue(getCurrency()).doubleValue() + "\n");
        });
        stringBuilder.append("\n*** Total Portfolio estimated value: "
                + getCurrency() + " " +  getPortfolioValue().doubleValue() + " ***");
        return stringBuilder.toString();
    }
}
