package com.amazon.scraper.models.scrape;

import com.amazon.scraper.extractors.ProductExtractor;
import com.amazon.scraper.models.product.AbstractProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;

/**
 * Created by ziv on 24/01/2017.
 */
public class ScrapeRequest {
    private String url;
    private AbstractProduct product;
    private ProductExtractor extractor;
    public ScrapeRequest(String url){
        this.url = url;
    }

    public AbstractProduct getProduct() {
        return product;
    }

    public void setProduct(AbstractProduct product) {
        this.product = product;
    }

    public ProductExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(ProductExtractor extractor) {
        this.extractor = extractor;
    }

    public String getUrl(){return this.url;}

    public boolean isValid(){
        boolean isValid = false;
        if (url != null && url.length() > 0) {
            isValid = true;
        }

        return isValid;
    }
}
