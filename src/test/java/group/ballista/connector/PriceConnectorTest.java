package group.ballista.connector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test PriceConnector")
class PriceConnectorTest {

    private final PriceConnector priceConnector = PriceConnector.getInstance();

    @Test
    @DisplayName("Connector should return valid price when valid asset and valid currency are provided")
    void testGetPriceSuccess() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice("BTC", "USD");
        assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Connector should return error price when valid asset and invalid currency are provided")
    void testGetPriceWrongCurrency() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice("BTC", "XX");
        assertEquals(0, price.compareTo(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("Connector should return error price when invalid asset and valid currency are provided")
    void testGetPriceWrongAsset() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice("WRONGASSET", "USD");
        assertEquals(0, price.compareTo(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("Connector should return error price when null asset is provided")
    void testGetPriceNullAsset() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice(null, "USD");
        assertEquals(0, price.compareTo(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("Connector should use default currency when null currency is provided")
    void testGetPriceNullCurrency() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice("DOT", null);
        assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Connector should use default currency when blank currency is provided")
    void testGetPriceBlankCurrency() {
        final BigDecimal price = priceConnector.getCryptoAssetPrice("DOT", "");
        assertTrue(price.compareTo(BigDecimal.ZERO) > 0);
    }
}
