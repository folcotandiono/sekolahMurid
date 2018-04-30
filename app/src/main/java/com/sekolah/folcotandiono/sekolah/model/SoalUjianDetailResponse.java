package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoalUjianDetailResponse {
    @SerializedName("list_soal_ujian_detail")
    private List<SoalUjianDetail> listSoalUjianDetail;

    public List<SoalUjianDetail> getListSoalUjianDetail() {
        return listSoalUjianDetail;
    }

    public void setListSoalUjianDetail(List<SoalUjianDetail> listSoalUjianDetail) {
        this.listSoalUjianDetail = listSoalUjianDetail;
    }
}
