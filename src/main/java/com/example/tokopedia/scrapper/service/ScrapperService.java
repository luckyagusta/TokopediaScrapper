package com.example.tokopedia.scrapper.service;

import com.example.tokopedia.scrapper.config.WebDriverConfig;
import com.example.tokopedia.scrapper.model.Product;
import com.example.tokopedia.scrapper.util.exception.ScrappingException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.example.tokopedia.scrapper.util.Path.*;

/**
 * @author Lucky Andreas Agusta
 */

public class ScrapperService {

    public String downloadProductListCsv()
            throws ScrappingException {
        String filename = PRODUCT + UNDERSCORE + "HANDPHONE"
                + UNDERSCORE + System.currentTimeMillis() + CSV_EXT;
        List<Product> products = extractProductList();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        csvMapper.addMixIn(Product.class, Product.ProductFormat.class);
        CsvSchema schema = csvMapper.schemaFor(Product.class).withHeader();

        try {
            File file = new File(filename);
            csvMapper.writer(schema).writeValue(file, products);
            return filename;
        } catch (IOException | RuntimeException e) {
            throw new ScrappingException(e.getMessage());
        }
    }

    public List<Product> extractProductList()
            throws ScrappingException {
        final WebDriverConfig webDriver = new WebDriverConfig();
        final List<Product> products = new ArrayList<>(100);

        try {

            List<String> tabs = webDriver.prepareTwoTabs();
            for (int page = 1; products.size() < 2; page++) {
                String url = BASE_URL + PAGE + page;
                final List<WebElement> items = webDriver.getElementListByScrollingDown(url,
                        XPATH_PRODUCT_LIST, tabs.get(0)); // switch to main tab

                for (WebElement item : items) {
                    String path = item.findElement(By.xpath(XPATH_PRODUCT_LINK)).getAttribute(HREF);
                    if (isTopAdsLink(path)) {
                        path = extractTopAdsLink(path);
                    }

                    webDriver.getWebpage(path, tabs.get(1)); //switch to new tab

                    // trigger lazy load
                    webDriver.scrollDownSmall();
                    webDriver.waitOnElement(XPATH_MERCHANT_NAME);

                    products.add(extractProduct(webDriver));

                    if (products.size() == 100) {
                        break;
                    }
                    webDriver.switchTab(tabs.get(0)); //switches to main tab
                }
            }

        } catch (Exception e) {
            throw new ScrappingException(e.getMessage());
        } finally {
            webDriver.quit();
        }

        return products;
    }

    private Product extractProduct(WebDriverConfig webDriver) {
        String name = webDriver.getText(XPATH_PRODUCT_NAME);
        String desc = webDriver.getText(XPATH_PRODUCT_DESCRIPTION);
        String imageLink = webDriver.getText(XPATH_PRODUCT_IMG_LINK, SRC);
        String price = webDriver.getText(XPATH_PRODUCT_PRICE)
                .split(RUPIAH_SIGN)[1].replace(DOT, EMPTY);
        String merchant = webDriver.getText(XPATH_MERCHANT_NAME);
        String rating = webDriver.getText(XPATH_PRODUCT_RATING);

        return Product.builder()
                .name(name)
                .description(desc)
                .imageLink(imageLink)
                .merchant(merchant)
                .price(Double.parseDouble(price))
                .rating(rating == null || rating.isEmpty() ? 0.0 : Double.parseDouble(rating))
                .build();
    }

    private boolean isTopAdsLink(String path) {
        return path.contains(TOP_ADS_URL);
    }

    private String extractTopAdsLink(String path) throws IOException {
        return URLDecoder.decode(path.substring(path.indexOf(PARAM_R) + 2).split(AMP)[0],
                StandardCharsets.UTF_8.name());
    }
}