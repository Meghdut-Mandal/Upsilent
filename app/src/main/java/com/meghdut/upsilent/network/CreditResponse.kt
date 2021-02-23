package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.Cast
import java.util.*

/**
 * Created by Meghdut Mandal on 10/02/17.
 */
class CreditResponse {
    @SerializedName("cast")
    var cast: ArrayList<Cast> = arrayListOf()
        private set

    fun setMovieCast(cast: ArrayList<Cast>) {
        this.cast = cast
    }
}