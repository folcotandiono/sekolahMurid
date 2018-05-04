package com.sekolah.folcotandiono.sekolah.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sekolah.folcotandiono.sekolah.IkutUjianActivity;
import com.sekolah.folcotandiono.sekolah.R;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.WaktuResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_SOAL_UJIAN;
import static com.sekolah.folcotandiono.sekolah.api.ApiClient.BASE_UPLOADS;

/**
 * Created by folcotandiono on 4/25/2018.
 */

public class SoalUjianDetailAdapter extends RecyclerView.Adapter<SoalUjianDetailAdapter.ViewHolder> {
    private List<SoalUjianDetail> listSoalUjianDetail;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static ApiInterface apiInterface;
    public static final int GALLERY_GAMBAR = 0;
    public static final int CAMERA_GAMBAR = 1;

    private static List<String> listJawaban;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView id;
        private TextView position;
        private TextView soalTulisan;
        private ImageView soalGambar;
        private TextView pilihanJawabanATulisan;
        private ImageView pilihanJawabanAGambar;
        private TextView pilihanJawabanBTulisan;
        private ImageView pilihanJawabanBGambar;
        private TextView pilihanJawabanCTulisan;
        private ImageView pilihanJawabanCGambar;
        private TextView pilihanJawabanDTulisan;
        private ImageView pilihanJawabanDGambar;
        private TextView pilihanJawabanETulisan;
        private ImageView pilihanJawabanEGambar;
        private EditText jawaban;
//        private Button pilih;
//        private ImageView gambar;
        public ViewHolder(final View v) {
            super(v);
            id = v.findViewById(R.id.id);
            position = v.findViewById(R.id.position);
            soalTulisan = v.findViewById(R.id.soal_tulisan);
            soalGambar = v.findViewById(R.id.soal_gambar);
            pilihanJawabanATulisan = v.findViewById(R.id.pilihan_jawaban_a_tulisan);
            pilihanJawabanAGambar = v.findViewById(R.id.pilihan_jawaban_a_gambar);
            pilihanJawabanBTulisan = v.findViewById(R.id.pilihan_jawaban_b_tulisan);
            pilihanJawabanBGambar = v.findViewById(R.id.pilihan_jawaban_b_gambar);
            pilihanJawabanCTulisan = v.findViewById(R.id.pilihan_jawaban_c_tulisan);
            pilihanJawabanCGambar = v.findViewById(R.id.pilihan_jawaban_c_gambar);
            pilihanJawabanDTulisan = v.findViewById(R.id.pilihan_jawaban_d_tulisan);
            pilihanJawabanDGambar = v.findViewById(R.id.pilihan_jawaban_d_gambar);
            pilihanJawabanETulisan = v.findViewById(R.id.pilihan_jawaban_e_tulisan);
            pilihanJawabanEGambar = v.findViewById(R.id.pilihan_jawaban_e_gambar);
            jawaban = v.findViewById(R.id.jawaban);

            jawaban.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listJawaban.set(Integer.valueOf(position.getText().toString()), s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
//            pilih = v.findViewById(R.id.pilih);
//            gambar = v.findViewById(R.id.gambar);
//            pilih.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showGambarPictureDialog();
//                }
//
//                private void showGambarPictureDialog() {
//                    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(v.getContext());
////        pictureDialog.setTitle("Select Action");
//                    String[] pictureDialogItems = {
//                            "Gallery",
//                            "Camera" };
//                    pictureDialog.setItems(pictureDialogItems,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    switch (which) {
//                                        case 0:
//                                            chooseGambarFromGallery();
//                                            break;
//                                        case 1:
//                                            takeGambarFromCamera();
//                                            break;
//                                    }
//                                }
//                            });
//                    pictureDialog.show();
//                }
//
//                public void chooseGambarFromGallery() {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
////                    galleryIntent.putExtra("position", position.getText().toString());
//
//                    ((Activity) v.getContext()).startActivityForResult(galleryIntent, GALLERY_GAMBAR);
//                }
//
//                private void takeGambarFromCamera() {
//                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                    intent.putExtra("posisi", position.getText().toString());
//                    ((Activity) v.getContext()).startActivityForResult(intent, CAMERA_GAMBAR);
//                }
//            });
            
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SoalUjianDetailAdapter(List<SoalUjianDetail> listSoalUjianDetail) {
        this.listSoalUjianDetail = listSoalUjianDetail;
        listJawaban = new ArrayList<>();
        for (int i = 0; i < listSoalUjianDetail.size(); i++) listJawaban.add("");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SoalUjianDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_soal_ujian_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.id.setText(listSoalUjianDetail.get(position).getId());
        holder.position.setText(String.valueOf(position));
        holder.soalTulisan.setText(listSoalUjianDetail.get(position).getSoalTulisan());
        try {
            JSONArray soalGambar = new JSONArray(listSoalUjianDetail.get(position).getSoalGambar());
            for (int i = 0; i < soalGambar.length(); i++) {
                if (!soalGambar.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + soalGambar.getString(i)).resize(500, 500).into(holder.soalGambar);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray pilihanJawabanTulisan = new JSONArray(listSoalUjianDetail.get(position).getPilihanJawabanTulisan());
            holder.pilihanJawabanATulisan.setText(pilihanJawabanTulisan.getString(0));
            holder.pilihanJawabanBTulisan.setText(pilihanJawabanTulisan.getString(1));
            holder.pilihanJawabanCTulisan.setText(pilihanJawabanTulisan.getString(2));
            holder.pilihanJawabanDTulisan.setText(pilihanJawabanTulisan.getString(3));
            holder.pilihanJawabanETulisan.setText(pilihanJawabanTulisan.getString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray pilihanJawabanGambar = new JSONArray(listSoalUjianDetail.get(position).getPilihanJawabanGambar());
            JSONArray pilihanJawabanGambarA = pilihanJawabanGambar.getJSONArray(0);
            for (int i = 0; i < pilihanJawabanGambarA.length(); i++) {
                if (!pilihanJawabanGambarA.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + pilihanJawabanGambarA.getString(i)).resize(500, 500).into(holder.pilihanJawabanAGambar);
                }
            }
            JSONArray pilihanJawabanGambarB = pilihanJawabanGambar.getJSONArray(1);
            for (int i = 0; i < pilihanJawabanGambarB.length(); i++) {
                if (!pilihanJawabanGambarB.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + pilihanJawabanGambarB.getString(i)).resize(500, 500).into(holder.pilihanJawabanBGambar);
                }
            }
            JSONArray pilihanJawabanGambarC = pilihanJawabanGambar.getJSONArray(2);
            for (int i = 0; i < pilihanJawabanGambarC.length(); i++) {
                if (!pilihanJawabanGambarC.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + pilihanJawabanGambarC.getString(i)).resize(500, 500).into(holder.pilihanJawabanCGambar);
                }
            }
            JSONArray pilihanJawabanGambarD = pilihanJawabanGambar.getJSONArray(3);
            for (int i = 0; i < pilihanJawabanGambarD.length(); i++) {
                if (!pilihanJawabanGambarD.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + pilihanJawabanGambarD.getString(i)).resize(500, 500).into(holder.pilihanJawabanDGambar);
                }
            }
            JSONArray pilihanJawabanGambarE = pilihanJawabanGambar.getJSONArray(4);
            for (int i = 0; i < pilihanJawabanGambarE.length(); i++) {
                if (!pilihanJawabanGambarE.getString(i).isEmpty()) {
                    Picasso.get().load(BASE_UPLOADS + pilihanJawabanGambarE.getString(i)).resize(500, 500).into(holder.pilihanJawabanEGambar);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listSoalUjianDetail.size();
    }

    public List<String> getListJawaban() {
        return listJawaban;
    }
}

