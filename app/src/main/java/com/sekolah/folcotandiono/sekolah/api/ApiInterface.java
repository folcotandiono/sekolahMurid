package com.sekolah.folcotandiono.sekolah.api;

import com.sekolah.folcotandiono.sekolah.model.JadwalUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.JawabanSoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.JawabanSoalUjianDetailResponse;
import com.sekolah.folcotandiono.sekolah.model.Murid;
import com.sekolah.folcotandiono.sekolah.model.MuridLoginResponse;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetailResponse;
import com.sekolah.folcotandiono.sekolah.model.SudahUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.WaktuResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by folcotandiono on 4/18/2018.
 */

public interface ApiInterface {
    @POST("murid/login")
    Call<MuridLoginResponse> muridLogin(@Body Murid murid);

    @GET("murid/waktu")
    Call<WaktuResponse> getWaktu();

    @GET("murid/data_jadwal_ujian")
    Call<JadwalUjianResponse> getDataJadwalUjian(@QueryMap Map<String, String> param);

    @GET("murid/data_soal_ujian_detail_by_id_soal_ujian")
    Call<SoalUjianDetailResponse> getDataSoalUjianDetailByIdSoalUjian(@QueryMap Map<String, String> param);

    @POST("murid/tambah_jawaban_soal_ujian_detail")
    Call<JawabanSoalUjianDetailResponse> tambahJawabanSoalUjianDetail(@Body JawabanSoalUjianDetail jawabanSoalUjianDetail);

    @GET("murid/sudah_ujian")
    Call<SudahUjianResponse> getSudahUjian(@QueryMap Map<String, String> param);
}
