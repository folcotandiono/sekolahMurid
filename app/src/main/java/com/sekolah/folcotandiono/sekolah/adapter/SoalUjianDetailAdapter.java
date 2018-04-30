package com.sekolah.folcotandiono.sekolah.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sekolah.folcotandiono.sekolah.IkutUjianActivity;
import com.sekolah.folcotandiono.sekolah.R;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.model.SoalUjianDetail;
import com.sekolah.folcotandiono.sekolah.model.WaktuResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_SOAL_UJIAN;

/**
 * Created by folcotandiono on 4/25/2018.
 */

public class SoalUjianDetailAdapter extends RecyclerView.Adapter<SoalUjianDetailAdapter.ViewHolder> {
    private List<SoalUjianDetail> listSoalUjianDetail;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static ApiInterface apiInterface;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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
        public ViewHolder(final View v) {
            super(v);
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
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SoalUjianDetailAdapter(List<SoalUjianDetail> listSoalUjianDetail) {
        this.listSoalUjianDetail = listSoalUjianDetail;
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
        holder.soalTulisan.setText(listSoalUjianDetail.get(position).getSoalTulisan());
        String pilihanJawabanTulisan = listSoalUjianDetail.get(position).getPilihanJawabanTulisan();
        holder.pilihanJawabanATulisan.setText(pilihanJawabanTulisan.indexOf(0));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listSoalUjianDetail.size();
    }
}

