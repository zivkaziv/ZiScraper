package com.amazon.scraper.extractors.amazon;

import com.amazon.scraper.extractors.AbstractProductExtractorImpl;
import com.amazon.scraper.models.product.AbstractProduct;
import com.amazon.scraper.models.product.amazon.AmazonProductReview;
import com.amazon.scraper.models.product.amazon.AmazonProductReviewer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Created by ziv on 24/01/2017.
 */
public class AmazonReviewsExtractorImpl extends AbstractProductExtractorImpl {
    public static final int NUM_OF_THREADS = 2;

    private String REVIEW_URL = "https://www.amazon.com/product-reviews/%s/ref=cm_cr_arp_d_show_all?reviewerType=all_reviews&pageNumber=%s";

    //SELECTORS
    public static final String REVIEWS_PAGE_SELECTOR = ".a-pagination";
    public static final String REVIEWS_SELECTOR = "div[data-hook='review']";
    public static final String SCORE_SELECTOR = "[data-hook='review-star-rating']";
    public static final String TITLE_SELECTOR = "[data-hook='review-title']";
    public static final String BODY_SELECTOR = "[data-hook='review-body']";
    public static final String REVIEWER_SELECTOR = "[data-hook='review-author']";
    public static final String REVIEWER_ID_SELECTOR = "href";


    protected void extractImpl(AbstractProduct product, Elements html) throws IOException {
        if(product.getHandleAsync()){
            extractAsync(product,html);
        }else{
            extractSync(product,html);
        }
    }

    protected void extractSync(AbstractProduct product, Elements html){
        Integer pageNumber = 1;
        boolean noMoreReviews =false;
        while (!noMoreReviews){
            List<AmazonProductReview> extractedReviews = extractReviewsFromSinglePageSync(pageNumber,product);
            if(extractedReviews.size() > 0) {
                product.getReviews().addAll(extractedReviews);
                pageNumber++;
            }else{
                noMoreReviews = true;
            }
        }
    }

    protected void extractAsync(AbstractProduct product, Elements html) throws IOException {
        int totalPages = getTotalPages(product);

        if(totalPages > 0) {
            ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_OF_THREADS);
            try {
                forkJoinPool.submit(() -> {
                    //parallel task here, for example
                    try {
                        IntStream.range(1, totalPages).forEach(i -> extractReviewsFromSinglePageAsync(i, product));
                    } catch (Exception e) {

                    }

                }).join();
            } catch (Exception e) {

            }
        }
    }

    //In this method we are checking how many pages of review we have in order to run parallel on them.
    //The way we are checking it is by checking the paging which is build from this way
    //1,2...5 next->
    //We are find the last index and add one (since we've found that sometimes we are having another reviews that aren't visible)
    //So this method will find the paging, take the last number which is 5 and then add one, and will return 6
    private int getTotalPages(AbstractProduct product) throws IOException {
        int totalPages = 0;
        Elements htmlPaging = Jsoup.connect(String.format(REVIEW_URL, product.getId(), 1)).get().select(REVIEWS_PAGE_SELECTOR).first().children();
        //We must check that we have any review
        if(htmlPaging.size() > 0){
            totalPages = Integer.parseInt(htmlPaging.get(htmlPaging.size() - 2).text()) + 1;
        }
        return totalPages;
    }

    //Extract review from since page and don't add the reviews to the list
    private List<AmazonProductReview> extractReviewsFromSinglePageSync(int pageNumber, AbstractProduct product){
        List<AmazonProductReview> reviews = new ArrayList<>();
        try {
            Elements reviewsHtml = getReviewsElements(pageNumber, product);
            for (Element reviewHtml : reviewsHtml) {
                try {
                    reviews.add(extractSingleReview(reviewHtml));
                }catch (Exception e){

                }
            }
        }catch (Exception e){

        }
        return reviews;
    }

    //The major difference is that in this method we are adding the reviews directly to the product model
    private void extractReviewsFromSinglePageAsync(int pageNumber,AbstractProduct product){
        try {
            Elements reviewsHtml = getReviewsElements(pageNumber, product);
            for (Element reviewHtml : reviewsHtml) {
                try {
                    product.getReviews().add(extractSingleReview(reviewHtml));
                }catch (Exception e){

                }
            }
        }catch (Exception e){

        }
    }

    private Elements getReviewsElements(int pageNumber, AbstractProduct product) throws IOException {
        Document document = Jsoup.connect(String.format(REVIEW_URL, product.getId(), pageNumber)).get();
        return document.select(REVIEWS_SELECTOR);
    }

    private AmazonProductReview extractSingleReview(Element reviewHtml){
        AmazonProductReview review = new AmazonProductReview();
        review.setId(reviewHtml.id());

        //handle score
        String scoreStr = reviewHtml.select(SCORE_SELECTOR).text();
        Scanner sc = new Scanner(scoreStr); //5.0 out of 5 stars
        review.setScore(sc.nextDouble());

        //handle title
        review.setTitle(reviewHtml.select(TITLE_SELECTOR).text());

        //handle text
        review.setBody(reviewHtml.select(BODY_SELECTOR).text());

        Elements reviewerHtml = reviewHtml.select(REVIEWER_SELECTOR);
        review.setReviewer(extractReviewer(reviewerHtml));
        return review;
    }

    private AmazonProductReviewer extractReviewer(Elements reviewHtml){

        AmazonProductReviewer reviewer = new AmazonProductReviewer();
        reviewer.setId(reviewHtml.attr(REVIEWER_ID_SELECTOR).split("/")[4]);
        reviewer.setName(reviewHtml.text());

        return reviewer;
    }
}
