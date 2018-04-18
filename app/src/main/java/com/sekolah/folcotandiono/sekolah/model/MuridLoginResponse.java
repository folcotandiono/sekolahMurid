package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by folcotandiono on 4/18/2018.
 */

public class MuridLoginResponse {
    @SerializedName("list_murid")
    private List<Murid> listMurid = new ArrayList<>();

    public List<Murid> getListMurid() {
        return listMurid;
    }

    public void setListMurid(List<Murid> listMurid) {
        this.listMurid = listMurid;
    }
}
