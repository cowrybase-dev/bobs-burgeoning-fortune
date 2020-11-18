package group.ballista.model;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * This class represents a portfolio item
 * consisting of a crypto asset and the bag size
 */
public class PortfolioItem {

    private final static Logger LOGGER = Logger.getLogger(PortfolioItem.class.getName());

    private final CryptoCurrency cryptoCurrency;
    private BigDecimal bagSize;

    public PortfolioItem(CryptoCurrency cryptoCurrency, String bagSize) {
        this.cryptoCurrency = cryptoCurrency;
        try {
            // the bag size has to be a positive number
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

    /**
     * Gets the total value of a crypto item.
     * Returns 0 if for whatever reason there is an error
     * in getting the price e.g. non-existing asset name or
     * negative bag size
     * @param currency
     * @return
     */
    public BigDecimal getBagValue(final String currency) {
        final BigDecimal price = cryptoCurrency.getAssetPrice(currency);
        if (price.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return BigDecimal.ZERO;
        }
        return price.multiply(getBagSize());
    }
}
