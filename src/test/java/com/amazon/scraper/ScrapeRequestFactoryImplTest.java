package com.amazon.scraper;

import com.amazon.scraper.exceptions.UnknownScrapeRequestException;
import com.amazon.scraper.extractors.amazon.AmazonProductExtractorImpl;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import com.amazon.scraper.models.scrape.ScrapeRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ziv on 25/01/2017.
 */
public class ScrapeRequestFactoryImplTest {
    private static ScrapeRequestFactoryImpl scrapeRequestFactoryImpl;

    @Before
    public void setUp() throws Exception {
        scrapeRequestFactoryImpl = new ScrapeRequestFactoryImpl();
    }

    @Test
    public void createAmazonRequest() throws Exception {
        //Arrange
        String url = "https://www.amazon.com/GoPro-CHDHX-501-HERO5-Black/dp/B01M14ATO0/ref=sr_1_1?s=electronics&ie=UTF8&qid=1485205507&sr=1-1&keywords=gopro5";

        //Act
        ScrapeRequest request = scrapeRequestFactoryImpl.createRequest(url);

        //Assert
        assertNotNull(request);
        assertTrue(request.isValid());
        assertNotNull(request.getExtractor());
        assertNotNull(request.getProduct());
        assertThat(request.getExtractor(), instanceOf(AmazonProductExtractorImpl.class));
        assertThat(request.getProduct(), instanceOf(AmazonProduct.class));
    }

    @Test
    public void createUnsupportedTypeRequest() throws Exception {
        //Arrange
        String url = "www.walla.co.il";

        //Act
        try {
            ScrapeRequest request = scrapeRequestFactoryImpl.createRequest(url);
        }catch (UnknownScrapeRequestException e){
            //Assert
            assertTrue(true);
            assertNotNull(e.getMessage());
        }catch (Exception e){//Fail the test in this case
            assertTrue(false);
        }
    }

    @Test
    public void createNullUrl() throws Exception {
        //Arrange
        String url = null;

        //Act
        ScrapeRequest request = scrapeRequestFactoryImpl.createRequest(url);

        //Assert
        assertFalse(request.isValid());
    }

    @Test
    public void createEmptyUrl() throws Exception {
        //Arrange
        String url = "";

        //Act
        ScrapeRequest request = scrapeRequestFactoryImpl.createRequest(url);

        //Assert
        assertFalse(request.isValid());
    }

}