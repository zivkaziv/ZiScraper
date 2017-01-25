package com.amazon.scraper.extractors.amazon;

import com.amazon.scraper.ScrapeRequestFactoryImpl;
import com.amazon.scraper.models.product.AbstractProduct;
import com.amazon.scraper.models.product.AbstractProductReview;
import com.amazon.scraper.models.product.amazon.AmazonProduct;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;

/**
 * Created by ziv on 25/01/2017.
 */
public class AmazonReviewsExtractorImplTest {
    private static AmazonReviewsExtractorImpl extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new AmazonReviewsExtractorImpl();
    }

    @Test
    public void extractNoReviews() throws Exception {
        AmazonProduct mockProduct = Mockito.mock(AmazonProduct.class);
        Elements mockHtml = Mockito.mock(Elements.class);

        //Act
        extractor.extract(mockProduct,mockHtml);

        //Assert
        assertEquals(mockProduct.getReviews().size(),0);
    }

}