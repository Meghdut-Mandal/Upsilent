package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.Genre
import com.meghdut.upsilent.models.Video
import java.util.*

/**
 * Created by Meghdut Mandal on 24/01/17.
 */
class AboutMovieResponse {
    @SerializedName("overview")
    var overview: String = ""

    @SerializedName("release_date")
    var releaseDate: String = ""

    @SerializedName("runtime")
    var runTimeOfMovie = 0

    @SerializedName("budget")
    var budget: Long = 0

    @SerializedName("revenue")
    var revenue: Long = 0

    @SerializedName("genres")
    var genres: ArrayList<Genre> = arrayListOf()

    @SerializedName("videos")
    var video: Video? = null
}