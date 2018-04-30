package com.sekolah.folcotandiono.sekolah;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sekolah.folcotandiono.sekolah.adapter.JadwalUjianAdapter;
import com.sekolah.folcotandiono.sekolah.adapter.SoalUjianDetailAdapter;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetailResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_SOAL_UJIAN;

public class IkutUjianActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private JadwalUjian jadwalUjian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikut_ujian);

        initView();
        initObject();
        initListener();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void initObject() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ujian");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initListener() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Map<String, String> param = new HashMap<>();
        param.put(ID_SOAL_UJIAN, getIntent().getStringExtra(ID_SOAL_UJIAN));

        Call<SoalUjianDetailResponse> call = apiInterface.getDataSoalUjianDetailByIdSoalUjian(param);
        call.enqueue(new Callback<SoalUjianDetailResponse>() {
            @Override
            public void onResponse(Call<SoalUjianDetailResponse> call, Response<SoalUjianDetailResponse> response) {
                List<SoalUjianDetail> listSoalUjianDetail = response.body().getListSoalUjianDetail();
                SoalUjianDetailAdapter soalUjianDetailAdapter = new SoalUjianDetailAdapter(listSoalUjianDetail);
                recyclerView.setAdapter(soalUjianDetailAdapter);
            }

            @Override
            public void onFailure(Call<SoalUjianDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
