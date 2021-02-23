package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.Review
import java.util.*

/**
 * Created by Meghdut Mandal on 14/03/17.
 */
class ReviewResponse {
    @SerializedName("results")
    var reviews: ArrayList<Review> = arrayListOf()
}