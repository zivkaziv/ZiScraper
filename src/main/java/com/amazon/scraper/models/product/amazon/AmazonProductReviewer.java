package com.amazon.scraper.models.product.amazon;

import org.jsoup.nodes.Element;

/**
 * Created by ziv on 23/01/2017.
 */
public class AmazonProductReviewer {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + " " + this.name;
    }
}
