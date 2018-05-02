package com.sekolah.folcotandiono.sekolah.api;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sekolah.folcotandiono.sekolah.LoginActivity.IP_ADDRESS;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.LOGIN;

/**
 * Created by folcotandiono on 4/18/2018.
 */

public class ApiClient {
    public static String BASE_IP_ADDRESS;
    public static String BASE_URL = "http://192.168.43.166/sekolah/index.php/";
    public static String BASE_UPLOADS = "http://192.168.43.166/sekolah/uploads/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN, 0);
        BASE_IP_ADDRESS = sharedPreferences.getString(IP_ADDRESS, null);
        BASE_URL = "http://" + BASE_IP_ADDRESS + "/sekolah/index.php/";
        BASE_UPLOADS = "http://" + BASE_IP_ADDRESS + "/sekolah/uploads/";

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
