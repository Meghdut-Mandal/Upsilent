package com.meghdut.upsilent.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Meghdut Mandal on 08/02/17.
 */
class TVShow : Serializable {
    @SerializedName("name")
    var title: String = ""

    @SerializedName("first_air_date")
    var date: String = ""

    @SerializedName("vote_average")
    var rating = 0.0

    @SerializedName("poster_path")
    var posterPath: String = ""

    @SerializedName("id")
    var id = 0
}