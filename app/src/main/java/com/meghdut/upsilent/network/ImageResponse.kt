package com.meghdut.upsilent.network;

import com.meghdut.upsilent.models.BackdropImage;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meghdut Mandal on 20/01/17.
 */

public class ImageResponse {
    @SerializedName("backdrops")
    private ArrayList<BackdropImage> bannerImageLinks;

    public ArrayList<BackdropImage> getBannerImageLinks() {
        return bannerImageLinks;
    }

    public void setBannerImageLinks(ArrayList<BackdropImage> bannerImageLinks) {
        this.bannerImageLinks = bannerImageLinks;
    }
}
