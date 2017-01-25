package com.amazon.scraper;

import com.amazon.scraper.extractors.amazon.AmazonProductExtractorImpl;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import com.amazon.scraper.models.scrape.ScrapeRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

/**
 * Created by ziv on 25/01/2017.
 */
public class ScraperImplTest {
    private static Scraper scraper;

    @Before
    public void setUp() throws Exception {
        scraper = new ScraperImpl();
    }

    @Test
    public void scrapeValidRequestTest() throws Exception {
        //Arrange
        String url = "http://www.amazon.com";
        ScrapeRequest mockRequest  = Mockito.spy(new ScrapeRequest(url));
        AmazonProduct mockProduct =  Mockito.spy(new AmazonProduct());
        AmazonProductExtractorImpl mockExtractor =  Mockito.spy(new AmazonProductExtractorImpl());
        Mockito.when(mockRequest.getProduct()).thenReturn(mockProduct);
        Mockito.when(mockRequest.getExtractor()).thenReturn(mockExtractor);
        Mockito.when(mockRequest.getUrl()).thenReturn(url);
        Mockito.when(mockProduct.getName()).thenReturn("Mock");

        //Act
        scraper.scrape(mockRequest);

        //Assert
        Mockito.verify(mockRequest, atLeast(1)).getUrl();
        Mockito.verify(mockRequest, atLeast(1)).getProduct();
        Mockito.verify(mockRequest, atLeast(1)).getExtractor();
    }

    @Test
    public void scrapeValidInvalidRequest() throws Exception {
        //Arrange
        String url = "http://www.amazon.com";
        ScrapeRequest mockRequest  = Mockito.spy(new ScrapeRequest(url));
        AmazonProduct mockProduct =  Mockito.spy(new AmazonProduct());
        AmazonProductExtractorImpl mockExtractor =  Mockito.spy(new AmazonProductExtractorImpl());
        Mockito.when(mockRequest.getProduct()).thenReturn(mockProduct);
        Mockito.when(mockRequest.isValid()).thenReturn(false);
        Mockito.when(mockRequest.getExtractor()).thenReturn(mockExtractor);
        Mockito.when(mockRequest.getUrl()).thenReturn(url);
        Mockito.when(mockProduct.getName()).thenReturn("Mock");

        //Act
        scraper.scrape(mockRequest);

        //Assert
        Mockito.verify(mockRequest, times(0)).getUrl();
        Mockito.verify(mockRequest, times(0)).getProduct();
        Mockito.verify(mockRequest, times(0)).getExtractor();
    }


    //TBD add support
    @Ignore
    @Test
    public void startScraperAsync() throws Exception {

    }

    //TBD add support
    @Ignore
    @Test
    public void stopScraperAsync() throws Exception {

    }

    //TBD add support
    @Ignore
    @Test
    public void addScrapeRequestAsync() throws Exception {

    }

}