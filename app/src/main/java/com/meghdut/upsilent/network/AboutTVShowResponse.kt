package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.Genre
import com.meghdut.upsilent.models.TVShowsCreaters
import com.meghdut.upsilent.models.Video
import java.util.*

/**
 * Created by Meghdut Mandal on 09/02/17.
 */
class AboutTVShowResponse {
    @SerializedName("overview")
    var overview: String = ""

    @SerializedName("first_air_date")
    var firstAirDate: String = ""

    @SerializedName("last_air_date")
    var lastAirDate: String = ""

    @SerializedName("genres")
    var genres: ArrayList<Genre> = arrayListOf()

    @SerializedName("created_by")
    var tvShowsCreaters: ArrayList<TVShowsCreaters> = arrayListOf()

    @SerializedName("type")
    var showType: String = ""
    var status: String = ""

    @SerializedName("number_of_episodes")
    var episodes = 0

    @SerializedName("number_of_seasons")
    var seasons = 0

    @SerializedName("videos")
    var video: Video? = null
}