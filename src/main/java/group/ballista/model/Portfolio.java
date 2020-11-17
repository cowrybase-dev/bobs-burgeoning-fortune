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
        stringBuilder.append("====== Portfolio (in ").append(getCurrency()).append(") ======\n\n");
        this.cryptoCurrencies.forEach(item -> {
            final CryptoCurrency asset = item.getCryptoCurrency();
            stringBuilder.append("Asset: ")
                    .append(asset.getTicker())
                    .append("; Bag size: ")
                    .append(item.getBagSize().doubleValue())
                    .append("; Unit price: ")
                    .append(asset.getAssetPrice(getCurrency()).doubleValue())
                    .append("; Total value: ")
                    .append(item.getBagValue(getCurrency()).doubleValue())
                    .append("\n");
        });
        stringBuilder.append("\n*** Total Portfolio estimated value: ")
                .append(getCurrency())
                .append(" ")
                .append(getPortfolioValue().doubleValue())
                .append(" ***");
        return stringBuilder.toString();
    }
}
