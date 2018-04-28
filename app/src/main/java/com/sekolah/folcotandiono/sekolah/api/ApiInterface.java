package com.sekolah.folcotandiono.sekolah.api;

import com.sekolah.folcotandiono.sekolah.model.JadwalUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.Murid;
import com.sekolah.folcotandiono.sekolah.model.MuridLoginResponse;

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

    @GET("murid/data_jadwal_ujian")
    Call<JadwalUjianResponse> dataJadwalUjian(@QueryMap Map<String, String> param);
}
