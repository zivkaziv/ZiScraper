package com.amazon.scraper.extractors.amazon;

import com.amazon.scraper.models.product.amazon.AmazonProduct;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;

/**
 * Created by ziv on 25/01/2017.
 */
public class AmazonProductExtractorImplTest {
    private static AmazonProductExtractorImpl extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new AmazonProductExtractorImpl();
    }

    @Test
    public void extractTheRightFieldsTest() throws Exception {
        //Arrange
        String id = "123";
        String name = "nike";
        String category = "shoes";
        AmazonProduct mockProduct = Mockito.mock(AmazonProduct.class);
        Elements mockHtml = Mockito.mock(Elements.class);
        Elements mockCategoryElement = Mockito.mock(Elements.class);
        Elements mockNameElement = Mockito.mock(Elements.class);
        Elements mockIDElement = Mockito.mock(Elements.class);

        Mockito.when(mockHtml.select(extractor.CATEGORY_SELECTOR)).thenReturn(mockCategoryElement);
        Mockito.when(mockCategoryElement.attr(extractor.CATEGORY_ATTR)).thenReturn(category);

        Mockito.when(mockHtml.select(extractor.ID_SELECTOR)).thenReturn(mockIDElement);
        Mockito.when(mockIDElement.attr(extractor.ID_ATTR)).thenReturn(id);

        Mockito.when(mockHtml.select(extractor.NAME_SELECTOR)).thenReturn(mockNameElement);
        Mockito.when(mockNameElement.text()).thenReturn(name);

        //Act
        extractor.extract(mockProduct,mockHtml);

        //Assert
        Mockito.verify(mockProduct, atLeast(1)).setId(id);
        Mockito.verify(mockProduct, atLeast(1)).setCategory(category);
        Mockito.verify(mockProduct, atLeast(1)).setName(name);
    }
}