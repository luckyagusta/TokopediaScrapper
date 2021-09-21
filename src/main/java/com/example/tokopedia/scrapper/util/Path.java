package com.example.tokopedia.scrapper.util;

/**
 * @author Lucky Andreas Agusta
 */
public interface Path {
    String BASE_URL = "https://www.tokopedia.com/p/handphone-tablet/handphone";
    String TOP_ADS_URL = "https://ta.tokopedia.com/promo";
    String PAGE = "?page=";

    String XPATH_PRODUCT_LIST = "//div[@data-testid='lstCL2ProductList']/div";
    String XPATH_PRODUCT_LINK = "a[@data-testid='lnkProductContainer']";
    String XPATH_PRODUCT_NAME = "//h1[@data-testid='lblPDPDetailProductName']";
    String XPATH_PRODUCT_DESCRIPTION = "//*[@data-testid='lblPDPDescriptionProduk']";
    String XPATH_PRODUCT_IMG_LINK = "//*[@data-testid='PDPImageMain']//img";
    String XPATH_PRODUCT_PRICE = "//*[@data-testid='lblPDPDetailProductPrice']";
    String XPATH_PRODUCT_RATING = "//*[@data-testid='lblPDPDetailProductRatingNumber']";
    String XPATH_MERCHANT_NAME = "//*[@data-testid='llbPDPFooterShopName']//h2";

    String HREF = "href";
    String SRC = "src";
    String AMP = "&";
    String PARAM_R = "r=";
    String EMPTY = "";
    String DOT = ".";
    String RUPIAH_SIGN = "Rp";
    String UNDERSCORE = "_";
    String PRODUCT = "Product";
    String CSV_EXT = ".csv";

}
