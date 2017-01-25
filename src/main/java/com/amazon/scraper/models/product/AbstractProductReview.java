package com.amazon.scraper.models.product;

/**
 * Created by ziv on 24/01/2017.
 */
public abstract class AbstractProductReview {
    public String getId() {
        return id;
    }
    protected String id;

    public void setId(String id) {
        this.id = id;
    }

    public abstract void print();
}
