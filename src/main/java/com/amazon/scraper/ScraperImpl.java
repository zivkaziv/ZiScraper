package com.amazon.scraper;

import com.amazon.scraper.extractors.ProductExtractor;
import com.amazon.scraper.extractors.amazon.AmazonProductExtractorImpl;
import com.amazon.scraper.models.product.AbstractProduct;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import com.amazon.scraper.models.scrape.ScrapeRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ziv on 23/01/2017.
 */


public class ScraperImpl implements Scraper {

    public static final String HTML_GENERAL_SELECTOR = "html";

    private ConcurrentLinkedQueue<ScrapeRequest> requestsQueue;
    private AtomicBoolean scrapperEnable;

    public ScraperImpl(){
        requestsQueue = new ConcurrentLinkedQueue<ScrapeRequest>();
        scrapperEnable = new AtomicBoolean(false);
    }
    //Scrape method
    public void scrape(ScrapeRequest request) throws IOException {
        if(request.isValid()) {
            //time measure
            long startTime = System.currentTimeMillis();

            //Get the HTML
            Document htmlDom = Jsoup.connect(request.getUrl()).get();

            //Extract the data
            request.getExtractor().extract(request.getProduct(), htmlDom.select(HTML_GENERAL_SELECTOR));//The select is in order to work with the elements them selfs and not with the document object

            long stopTime = System.currentTimeMillis();
            System.out.println("Elapsed time for product " + request.getProduct().getName() + " was " + TimeUnit.MILLISECONDS.toSeconds(stopTime - startTime) + " seconds.");
        }
    }
}
