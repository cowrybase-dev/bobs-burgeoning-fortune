package group.ballista.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Portfolio Test")
class PortfolioTest {

    @Test
    @DisplayName("Value should be successfully calculated with right parameters")
    void testPortfolioValue() {
        Portfolio portfolio = new Portfolio("USD");
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("BTC"), "0.3"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("XRP"), "10000.0"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("EGLD"), "1000.0"));
        final BigDecimal value = portfolio.getPortfolioValue();
        assertSame("USD", portfolio.getCurrency());
        assertTrue(value.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Value calculation should fail with wrong currency")
    void testPortfolioWrongCurrency() {
        Portfolio portfolio = new Portfolio("EURSFS");
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("BTC"), "0.3"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("XRP"), "10000.0"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("EGLD"), "1000.0"));
        final BigDecimal value = portfolio.getPortfolioValue();
        assertEquals(0, value.compareTo(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Value should successfully be calculated even if wrong asset among provided asset. In this case its " +
            "value is just 0")
    void testPortfolioWrongAsset() {
        Portfolio portfolio = new Portfolio();
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("WRONGASSET"), "0.3"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("XRP"), "10000.0"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("EGLD"), "1000.0"));
        final BigDecimal value = portfolio.getPortfolioValue();
        assertSame("EUR", portfolio.getCurrency());
        assertTrue(value.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Value should successfully be calculated even if negative bag size provided. In this case its " +
            "value is just 0")
    void testPortfolioNegativePrice() {
        Portfolio portfolio = new Portfolio();
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("BTC"), "-0.3"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("XRP"), "wrongbagsize"));
        portfolio.addAsset(new PortfolioItem(new CryptoCurrency("EGLD"), "1000.0"));
        final BigDecimal value = portfolio.getPortfolioValue();
        assertSame("EUR", portfolio.getCurrency());
        assertTrue(value.compareTo(BigDecimal.ZERO) > 0);
    }
}
