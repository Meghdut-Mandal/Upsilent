package com.meghdut.upsilent.network

import com.google.gson.annotations.SerializedName
import com.meghdut.upsilent.models.BackdropImage
import java.util.*

/**
 * Created by Meghdut Mandal on 20/01/17.
 */
class ImageResponse {
    @SerializedName("backdrops")
    var bannerImageLinks: ArrayList<BackdropImage> = arrayListOf()
}