package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by folcotandiono on 4/29/2018.
 */

public class WaktuResponse {
    @SerializedName("waktu")
    private String waktu;

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
