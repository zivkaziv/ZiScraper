package com.amazon.scraper.models.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ziv on 24/01/2017.
 */
public abstract class AbstractProduct {


    //This boolean gives the option to get the product reviews async
    protected Boolean handle_async;

    protected String id;
    protected String name;
    protected String category;


    protected List<AbstractProductReview> reviews;
    protected Queue<AbstractProductReview> reviewsQueue = new ConcurrentLinkedQueue<AbstractProductReview>();

    public AbstractProduct(){
        this(false);
    }
    public AbstractProduct(Boolean handleAsync){
        reviews = new ArrayList();
        reviewsQueue = new ConcurrentLinkedQueue<AbstractProductReview>();
        handle_async = handleAsync;

    }

    public Collection<AbstractProductReview> getReviews() {
        if(!this.handle_async) {
            return reviews;
        }else{
            return reviewsQueue;
        }
    }

    public Boolean getHandleAsync() {
        return handle_async;
    }

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

    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public abstract void print();
}
