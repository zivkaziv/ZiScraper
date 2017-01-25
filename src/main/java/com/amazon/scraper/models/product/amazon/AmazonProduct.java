package com.amazon.scraper.models.product.amazon;

import com.amazon.scraper.models.product.AbstractProduct;
import com.amazon.scraper.models.product.AbstractProductReview;

/**
 * Created by ziv on 23/01/2017.
 */
public class AmazonProduct extends AbstractProduct {
    public AmazonProduct(){
        super();
    }

    public AmazonProduct(Boolean handleAsync){
        super(handleAsync);
    }

    @Override
    public void print(){
        System.out.println("Id          : " + this.getId());
        System.out.println("Name        : " + this.getName());
        System.out.println("Category    : " + this.getCategory());
        System.out.println("Reviews     : going to print " + this.getReviews().size() + " reviews ");
        for(AbstractProductReview review : getReviews()){
            review.print();
        }
        if(this.getHandleAsync()){
            System.out.println("======================Async===========================");
        }else{
            System.out.println("=======================Sync==================================");
        }
    }
}
