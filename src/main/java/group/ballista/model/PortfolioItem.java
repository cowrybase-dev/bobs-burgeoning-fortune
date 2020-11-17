package group.ballista.model;

import java.math.BigDecimal;

public class PortfolioItem {
    private final CryptoCurrency cryptoCurrency;
    private BigDecimal bagSize;

    public PortfolioItem(CryptoCurrency cryptoCurrency, String bagSize) {
        this.cryptoCurrency = cryptoCurrency;
        try {
            final BigDecimal bigDecimalBag = new BigDecimal(bagSize);
            if (bigDecimalBag.compareTo(BigDecimal.ZERO) < 0) {
                this.bagSize = BigDecimal.ZERO;
            } else {
                this.bagSize = bigDecimalBag;
            }
        } catch (NumberFormatException e) {
            this.bagSize = BigDecimal.ZERO;
        }
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public BigDecimal getBagSize() {
        return bagSize;
    }

    public BigDecimal getBagValue(final String currency) {
        final BigDecimal price = cryptoCurrency.getAssetPrice(currency);
        if (price.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return BigDecimal.ZERO;
        }
        return price.multiply(getBagSize());
    }
}
