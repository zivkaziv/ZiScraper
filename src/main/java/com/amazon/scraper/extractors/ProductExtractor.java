package com.amazon.scraper.extractors;

import com.amazon.scraper.models.product.AbstractProduct;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by ziv on 24/01/2017.
 */
public interface ProductExtractor {
    void extract(AbstractProduct product, Elements html) throws IOException;
}
