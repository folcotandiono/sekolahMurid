package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by folcotandiono on 5/1/2018.
 */

public class SudahUjianResponse {
    @SerializedName("banyak")
    private String banyak;

    public String getBanyak() {
        return banyak;
    }

    public void setBanyak(String banyak) {
        this.banyak = banyak;
    }
}
