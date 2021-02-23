package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.TVShow
import java.util.*

/**
 * Created by Meghdut Mandal on 08/02/17.
 */
class TVShowResponse {
    var total_results = 0
    var total_pages = 0

    @SerializedName("results")
    var tvShows: ArrayList<TVShow> = arrayListOf()
    var page = 0
}