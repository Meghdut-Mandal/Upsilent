package com.meghdut.upsilent.models

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Meghdut Mandal on 18/02/17.
 */
class Video {
    @SerializedName("results")
    var trailers: ArrayList<Trailer> = arrayListOf()
}