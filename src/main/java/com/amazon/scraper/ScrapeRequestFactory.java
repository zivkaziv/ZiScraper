package com.amazon.scraper;

import com.amazon.scraper.exceptions.UnknownScrapeRequestException;
import com.amazon.scraper.models.scrape.ScrapeRequest;

/**
 * Created by ziv on 24/01/2017.
 */
public interface ScrapeRequestFactory {

    ScrapeRequest createRequest(String url) throws UnknownScrapeRequestException;

}
