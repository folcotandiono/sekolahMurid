package com.sekolah.folcotandiono.sekolah.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by folcotandiono on 5/1/2018.
 */

public class JawabanSoalUjianDetail {
    @SerializedName("id")
    private String id;
    @SerializedName("id_soal_ujian_detail")
    private String idSoalUjianDetail;
    @SerializedName("id_murid")
    private String idMurid;
    @SerializedName("id_jadwal_ujian")
    private String idJadwalUjian;
    @SerializedName("jawaban_tulisan")
    private String jawabanTulisan;
    @SerializedName("jawaban_gambar")
    private String jawabanGambar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSoalUjianDetail() {
        return idSoalUjianDetail;
    }

    public void setIdSoalUjianDetail(String idSoalUjianDetail) {
        this.idSoalUjianDetail = idSoalUjianDetail;
    }

    public String getIdMurid() {
        return idMurid;
    }

    public void setIdMurid(String idMurid) {
        this.idMurid = idMurid;
    }

    public String getJawabanTulisan() {
        return jawabanTulisan;
    }

    public void setJawabanTulisan(String jawabanTulisan) {
        this.jawabanTulisan = jawabanTulisan;
    }

    public String getJawabanGambar() {
        return jawabanGambar;
    }

    public void setJawabanGambar(String jawabanGambar) {
        this.jawabanGambar = jawabanGambar;
    }

    public String getIdJadwalUjian() {
        return idJadwalUjian;
    }

    public void setIdJadwalUjian(String idJadwalUjian) {
        this.idJadwalUjian = idJadwalUjian;
    }
}
