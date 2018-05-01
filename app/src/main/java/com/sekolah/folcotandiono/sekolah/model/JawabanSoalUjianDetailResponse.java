package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by folcotandiono on 5/1/2018.
 */

public class JawabanSoalUjianDetailResponse {
    @SerializedName("list_jawaban_soal_ujian_detail")
    private List<JawabanSoalUjianDetail> listJawabanSoalUjianDetail;

    public List<JawabanSoalUjianDetail> getListJawabanSoalUjianDetail() {
        return listJawabanSoalUjianDetail;
    }

    public void setListJawabanSoalUjianDetail(List<JawabanSoalUjianDetail> listJawabanSoalUjianDetail) {
        this.listJawabanSoalUjianDetail = listJawabanSoalUjianDetail;
    }
}
