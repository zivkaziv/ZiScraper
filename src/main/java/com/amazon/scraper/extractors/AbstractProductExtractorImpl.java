package com.amazon.scraper.extractors;

import com.amazon.scraper.models.product.AbstractProduct;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziv on 24/01/2017.
 */
public abstract class AbstractProductExtractorImpl implements ProductExtractor{
    protected List<ProductExtractor> extractors = new ArrayList<ProductExtractor>();

    public AbstractProductExtractorImpl(){
    }

    public AbstractProductExtractorImpl(ProductExtractor extractor){
        extractors.add(extractor);
    }

    public AbstractProductExtractorImpl(List<ProductExtractor> extractors){
        this.extractors.addAll(extractors);
    }

    public void extract(AbstractProduct product, Elements html) throws IOException {
        extractImpl(product,html);
        //Run over all the sub extractors
        for(ProductExtractor extractor : extractors){
            extractor.extract(product,html);
        }
    }

    protected abstract void extractImpl(AbstractProduct product, Elements html) throws IOException;
}
