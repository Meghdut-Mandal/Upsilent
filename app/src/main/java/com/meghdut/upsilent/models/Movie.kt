package com.meghdut.upsilent.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Acer on 9/11/2016.
 */
class Movie : Serializable {
    @SerializedName("original_title")
    var title: String = ""

    @SerializedName("release_date")
    var date: String = ""

    @SerializedName("vote_average")
    var rating = 0.0

    @SerializedName("poster_path")
    var posterPath: String = ""

    @SerializedName("id")
    var id = 0
}