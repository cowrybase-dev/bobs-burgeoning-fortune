package group.ballista.model;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class PortfolioItem {

    private final static Logger LOGGER = Logger.getLogger(PortfolioItem.class.getName());

    private final CryptoCurrency cryptoCurrency;
    private BigDecimal bagSize;

    public PortfolioItem(CryptoCurrency cryptoCurrency, String bagSize) {
        this.cryptoCurrency = cryptoCurrency;
        try {
            final BigDecimal bigDecimalBag = new BigDecimal(bagSize);
            if (bigDecimalBag.compareTo(BigDecimal.ZERO) < 0) {
                LOGGER.severe(String.format("Bag size %s of asset %s can not be negative", bagSize, cryptoCurrency.getTicker()));
                this.bagSize = BigDecimal.ZERO;
            } else {
                this.bagSize = bigDecimalBag;
            }
        } catch (NumberFormatException e) {
            LOGGER.severe(String.format("Invalid bag size %s of asset %s. Please provide a valid bag size", bagSize, cryptoCurrency.getTicker()));
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
