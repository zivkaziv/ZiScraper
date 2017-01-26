package com.amazon.scraper;

import com.amazon.scraper.extractors.ProductExtractor;
import com.amazon.scraper.models.product.AbstractProduct;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import com.amazon.scraper.models.scrape.ScrapeRequest;

import java.io.IOException;

/**
 * Created by ziv on 23/01/2017.
 */
public interface Scraper {
    void scrape(ScrapeRequest request) throws IOException;
    void startScraperAsync() throws Exception;
    void stopScraperAsync() throws Exception;
    void addScrapeRequestAsync(ScrapeRequest request) throws Exception;
}
