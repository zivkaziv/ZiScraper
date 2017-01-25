package com.amazon.scraper;

import com.amazon.scraper.exceptions.UnknownScrapeRequestException;
import com.amazon.scraper.extractors.amazon.AmazonProductExtractorImpl;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import com.amazon.scraper.models.scrape.ScrapeRequest;

/**
 * Created by ziv on 24/01/2017.
 */
public class ScrapeRequestFactoryImpl implements ScrapeRequestFactory {

    public ScrapeRequest createRequest(String url) throws UnknownScrapeRequestException {
        ScrapeRequest request = new ScrapeRequest(url);
        if(request.isValid()){
            //Check if the url contains amazon. if this is the case, create the relevant product and the relevant extractor strategy
            //This is really useful for AB tests that those sites have which require sometime different strategy
            if(request.getUrl().indexOf(".amazon") > -1){
                request.setProduct(new AmazonProduct(true));
                request.setExtractor(new AmazonProductExtractorImpl());
            //Here we can add more implementations like ebay and etc..
            }else{
                throw new UnknownScrapeRequestException("The url " + url + " have unknown format");
            }
        }
        return request;
    }
}
