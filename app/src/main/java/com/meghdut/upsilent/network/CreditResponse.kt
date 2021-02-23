package com.meghdut.upsilent.network;

import com.meghdut.upsilent.models.Cast;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meghdut Mandal on 10/02/17.
 */

public class CreditResponse {

    @SerializedName("cast")
    private ArrayList<Cast> cast;

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setMovieCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

}
