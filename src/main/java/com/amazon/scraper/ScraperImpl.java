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
    public static final Integer SLEEP_TIME_MILIS = 2000;
    private Thread pullingScrapeRequestThread;

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

    //Async methods for worker queue
    public void startScraperAsync() throws Exception {
        scrapperEnable.getAndSet(true);
        pullingScrapeRequestThread = new Thread(()-> {
            while (scrapperEnable.getAndSet(true)) {
                try {
                    if(requestsQueue.size() > 0){
                        System.out.println("Polling request. The current status of the queue is " + requestsQueue.size());
                        scrape(requestsQueue.poll());
                    }else {
                        Thread.sleep(SLEEP_TIME_MILIS);
                        System.out.println("No scrape requests in queue - Going to sleep for " + SLEEP_TIME_MILIS);
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
        pullingScrapeRequestThread.start();
    }

    public void stopScraperAsync() throws Exception {
        scrapperEnable.getAndSet(false);
        pullingScrapeRequestThread.interrupt();
        System.out.println("Stopping the polling requests, we have  " + requestsQueue.size() + " scrape requests in the queue");
    }

    public void addScrapeRequestAsync(ScrapeRequest request) throws Exception {
        requestsQueue.add(request);
    }
}
