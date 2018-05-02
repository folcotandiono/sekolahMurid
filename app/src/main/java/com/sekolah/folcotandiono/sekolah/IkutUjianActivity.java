package com.sekolah.folcotandiono.sekolah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sekolah.folcotandiono.sekolah.adapter.JadwalUjianAdapter;
import com.sekolah.folcotandiono.sekolah.adapter.SoalUjianDetailAdapter;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.JawabanSoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.JawabanSoalUjianDetailResponse;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetailResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_JADWAL_UJIAN;
import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_SOAL_UJIAN;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.ID;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.LOGIN;
import static com.sekolah.folcotandiono.sekolah.adapter.SoalUjianDetailAdapter.CAMERA_GAMBAR;
import static com.sekolah.folcotandiono.sekolah.adapter.SoalUjianDetailAdapter.GALLERY_GAMBAR;
import static com.sekolah.folcotandiono.sekolah.api.ApiClient.BASE_UPLOADS;

public class IkutUjianActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Button simpan;

    private JadwalUjian jadwalUjian;
    private List<SoalUjianDetail> listSoalUjianDetail;

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
        simpan = findViewById(R.id.simpan);
    }

    private void initObject() {
        apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ujian");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initListener() {
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listSoalUjianDetail.size(); i++) {
                    View v = recyclerView.getChildAt(i);
                    JawabanSoalUjianDetail jawabanSoalUjianDetail = new JawabanSoalUjianDetail();

                    sharedPreferences = getSharedPreferences(LOGIN, 0);
                    jawabanSoalUjianDetail.setIdMurid(sharedPreferences.getString(ID, null));

                    TextView id = v.findViewById(R.id.id);
                    jawabanSoalUjianDetail.setIdSoalUjianDetail(id.getText().toString());

                    TextView jawaban = v.findViewById(R.id.jawaban);
                    jawabanSoalUjianDetail.setJawabanTulisan(jawaban.getText().toString());

                    jawabanSoalUjianDetail.setIdJadwalUjian(getIntent().getStringExtra(ID_JADWAL_UJIAN));

                    apiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                    apiInterface.tambahJawabanSoalUjianDetail(jawabanSoalUjianDetail).enqueue(new Callback<JawabanSoalUjianDetailResponse>() {
                        @Override
                        public void onResponse(Call<JawabanSoalUjianDetailResponse> call, Response<JawabanSoalUjianDetailResponse> response) {
//                            List<JawabanSoalUjianDetail> list = response.body().getListJawabanSoalUjianDetail();
                        }

                        @Override
                        public void onFailure(Call<JawabanSoalUjianDetailResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                Toast.makeText(IkutUjianActivity.this, "Jawaban sudah dikumpul", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
                listSoalUjianDetail = response.body().getListSoalUjianDetail();
                SoalUjianDetailAdapter soalUjianDetailAdapter = new SoalUjianDetailAdapter(listSoalUjianDetail);
                recyclerView.setAdapter(soalUjianDetailAdapter);
            }

            @Override
            public void onFailure(Call<SoalUjianDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY_GAMBAR) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
////                    String path = saveImage(bitmap);
////                    Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    View v = recyclerView.getChildAt(Integer.valueOf(data.getStringExtra("position")));
//                    ImageView gambar = v.findViewById(R.id.gambar);
//                    Picasso.get().load(contentURI).resize(500, 500).into(gambar);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
////                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA_GAMBAR) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            View v = recyclerView.getChildAt(Integer.valueOf(data.getStringExtra("position")));
//            ImageView gambar = v.findViewById(R.id.gambar);
//            Picasso.get().load(data.getData()).resize(500, 500).into(gambar);
////            saveImage(thumbnail);
////            Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
//    }
}
