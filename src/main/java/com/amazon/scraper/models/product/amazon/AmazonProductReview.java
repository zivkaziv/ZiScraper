package com.amazon.scraper.models.product.amazon;

import com.amazon.scraper.models.product.AbstractProductReview;
import org.jsoup.nodes.Element;

import java.util.Scanner;

/**
 * Created by ziv on 23/01/2017.
 */
public class AmazonProductReview extends AbstractProductReview {
    private Double score;
    private String body;
    private String title;
    private AmazonProductReviewer reviewer;

    public AmazonProductReview(){
        reviewer = new AmazonProductReviewer();
    }

    public AmazonProductReviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(AmazonProductReviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void print() {
        System.out.println("==============================================================");
        System.out.println("reviewer :" + reviewer.toString());
        System.out.println("score    :" + this.getScore());
        System.out.println("title    :" + this.getTitle());
        System.out.println("body     :" + this.getBody());
    }
}
