package com.sekolah.folcotandiono.sekolah.adapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.R;
import com.sekolah.folcotandiono.sekolah.model.WaktuResponse;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by folcotandiono on 4/25/2018.
 */

public class JadwalUjianAdapter extends RecyclerView.Adapter<JadwalUjianAdapter.ViewHolder> {
    private List<JadwalUjian> listJadwalUjian;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static ApiInterface apiInterface;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView id;
        private TextView nama;
        private TextView idSoalUjian;
        private TextView namaSoalUjian;
        private TextView tanggal;
        private TextView durasi;
        private Button ujian;
        public ViewHolder(final View v) {
            super(v);
            id = v.findViewById(R.id.id);
            nama = v.findViewById(R.id.nama);
            idSoalUjian = v.findViewById(R.id.id_soal_ujian);
            namaSoalUjian = v.findViewById(R.id.nama_soal_ujian);
            tanggal = v.findViewById(R.id.tanggal);
            durasi = v.findViewById(R.id.durasi);
            ujian = v.findViewById(R.id.ujian);
            ujian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Date start, end;
                    String datee = tanggal.getText().toString();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    try {
                        start = format.parse(datee);
                        end = start;
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(end);
                        calendar.add(Calendar.MINUTE, Integer.valueOf(durasi.getText().toString()));
                        Log.d("calendar", calendar.toString());
                        end = calendar.getTime();
                        Log.d("waktu", start.toString() + " " + end.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<WaktuResponse> call = apiInterface.getWaktu();
                    call.enqueue(new Callback<WaktuResponse>() {
                        @Override
                        public void onResponse(Call<WaktuResponse> call, Response<WaktuResponse> response) {
                            Date date = new Date(response.body().getWaktu());
                            Toast.makeText(v.getContext(), date.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<WaktuResponse> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public JadwalUjianAdapter(List<JadwalUjian> listJadwalUjian) {
        this.listJadwalUjian = listJadwalUjian;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JadwalUjianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_jadwal_ujian, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.id.setText(listJadwalUjian.get(position).getId());
        holder.nama.setText(listJadwalUjian.get(position).getNama());
        holder.idSoalUjian.setText(listJadwalUjian.get(position).getIdSoalUjian());
        holder.namaSoalUjian.setText(listJadwalUjian.get(position).getNamaSoalUjian());
        holder.tanggal.setText(listJadwalUjian.get(position).getTanggal());
        holder.durasi.setText(listJadwalUjian.get(position).getDurasi());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listJadwalUjian.size();
    }
}

