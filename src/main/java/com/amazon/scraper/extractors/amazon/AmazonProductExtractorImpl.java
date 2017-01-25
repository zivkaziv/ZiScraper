package com.amazon.scraper.extractors.amazon;

import com.amazon.scraper.extractors.AbstractProductExtractorImpl;
import com.amazon.scraper.extractors.ProductExtractor;
import com.amazon.scraper.models.product.AbstractProduct;
import org.jsoup.select.Elements;

/**
 * Created by ziv on 24/01/2017.
 */
public class AmazonProductExtractorImpl extends AbstractProductExtractorImpl {

    public static final String CATEGORY_SELECTOR = "#nav-subnav";
    public static final String CATEGORY_ATTR = "data-category";
    public static final String ID_ATTR = "data-asin";
    public static final String ID_SELECTOR = "#averageCustomerReviews";
    public static final String NAME_SELECTOR = "#productTitle";

    public AmazonProductExtractorImpl(){
        super(new AmazonReviewsExtractorImpl());
    }

    protected void extractImpl(AbstractProduct product, Elements html) {
        //Extract the product fields
        extractProductFields(product,html);
    }

    private void extractProductFields(AbstractProduct product, Elements html) {
        product.setCategory(html.select(CATEGORY_SELECTOR).attr(CATEGORY_ATTR));
        product.setId(html.select(ID_SELECTOR).attr(ID_ATTR));
        product.setName(html.select(NAME_SELECTOR).text());
    }
}
