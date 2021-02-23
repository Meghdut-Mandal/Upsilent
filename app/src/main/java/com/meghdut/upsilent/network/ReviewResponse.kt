package com.meghdut.upsilent.network;

import com.meghdut.upsilent.models.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meghdut Mandal on 14/03/17.
 */

public class ReviewResponse {

    @SerializedName("results")
    private ArrayList<Review> reviews;

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
