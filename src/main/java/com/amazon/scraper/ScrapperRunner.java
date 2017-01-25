package com.amazon.scraper;

import com.amazon.scraper.exceptions.UnknownScrapeRequestException;
import com.amazon.scraper.models.scrape.ScrapeRequest;

import java.io.IOException;

/**
 * Created by ziv on 24/01/2017.
 */
public class ScrapperRunner {

    public static void main(String[] args) {
        try {
            Scraper scraper = new ScraperImpl();
            scrape(scraper,args);
            //This method is running the scraper as worker queue scrape request
//            scrapeAsync(scraper, url);

        }catch (Exception e){

        }
    }

    private static void scrapeAsync(Scraper scraper, String[] urls) throws Exception {
        scraper.startScraperAsync();
        for(String url : urls) {
            ScrapeRequestFactoryImpl requestFactory = new ScrapeRequestFactoryImpl();
            ScrapeRequest request = requestFactory.createRequest(url);
            scraper.addScrapeRequestAsync(request);
            request.getProduct().print();
        }
    }

    private static void scrape(Scraper scraper, String[] urls) throws UnknownScrapeRequestException, IOException {
        for(String url : urls) {
            ScrapeRequestFactoryImpl requestFactory = new ScrapeRequestFactoryImpl();
            ScrapeRequest request = requestFactory.createRequest(url);
            scraper.scrape(request);
            request.getProduct().print();
        }
    }
}
