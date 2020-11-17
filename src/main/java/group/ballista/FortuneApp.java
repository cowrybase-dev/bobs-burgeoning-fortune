package group.ballista;

import group.ballista.model.CryptoCurrency;
import group.ballista.model.Portfolio;
import group.ballista.model.PortfolioItem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class FortuneApp {

    private final static Logger LOGGER = Logger.getLogger(FortuneApp.class.getName());

    public static void main(String[] args) {
        if (args.length == 1 && args[0].endsWith("bobs_crypto.txt")) {
            final Portfolio portfolio = portfolioFromFile(args[0], null);
            System.out.println(portfolio);
        } else if (args.length == 3 && args[0].endsWith("bobs_crypto.txt")) {
            if (args[1].equals("--c")) {
                final String currency = args[2];
                final Portfolio portfolio = portfolioFromFile(args[0], currency);
                System.out.println(portfolio);
            } else {
                printHelp();
            }
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        final String help = "Usage: java -jar <jarfile> <path to bobs_crypto.txt> [options]\n\n"
                + "where options include:\n\n"
                + "--c <currency>. Default is EUR";
        System.out.println(help);
    }

    private static Properties propertiesFromFile(final String file) {
        final Properties cryptoAssetFileAsProperties = new Properties();
        try {
            cryptoAssetFileAsProperties.load(new FileInputStream(file));
        } catch (IOException e) {
            LOGGER.severe(String.format("Failed to load the provided file. Please try again. Error: %s",
                    e.getMessage()));
        }
        return cryptoAssetFileAsProperties;
    }

    private static Portfolio portfolioFromFile(final String file, final String currency) {
        final Portfolio portfolio = new Portfolio();
        if (currency != null) {
            portfolio.setCurrency(currency);
        }
        propertiesFromFile(file).forEach((key, value) -> portfolio.addAsset(new PortfolioItem(new CryptoCurrency((String) key), (String) value)));
        return portfolio;
    }
}
