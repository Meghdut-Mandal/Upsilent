package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.Movie
import java.io.Serializable
import java.util.*

/**
 * Created by Meghdut Mandal on 12/01/17.
 */
class MovieResponse : Serializable {
    var total_results = 0
    var total_pages = 0

    @SerializedName("results")
    var movies: ArrayList<Movie> = arrayListOf()
    var page = 0
}