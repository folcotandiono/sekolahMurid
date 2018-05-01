package com.sekolah.folcotandiono.sekolah.adapter;

import android.content.Intent;
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

import com.sekolah.folcotandiono.sekolah.IkutUjianActivity;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.R;
import com.sekolah.folcotandiono.sekolah.model.SudahUjianResponse;
import com.sekolah.folcotandiono.sekolah.model.WaktuResponse;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_JADWAL_UJIAN;
import static com.sekolah.folcotandiono.sekolah.JadwalUjianFragment.ID_SOAL_UJIAN;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.ID;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.LOGIN;

/**
 * Created by folcotandiono on 4/25/2018.
 */

public class JadwalUjianAdapter extends RecyclerView.Adapter<JadwalUjianAdapter.ViewHolder> {
    public List<JadwalUjian> listJadwalUjian;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static ApiInterface apiInterface;

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
                    Date start = new Date(), end = new Date();
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

                    final Date startt = start, endd = end;

                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<WaktuResponse> call = apiInterface.getWaktu();
                    call.enqueue(new Callback<WaktuResponse>() {
                        @Override
                        public void onResponse(Call<WaktuResponse> call, Response<WaktuResponse> response) {
                            Date date = new Date(Long.valueOf(response.body().getWaktu()) * 1000);
                            if (date.after(startt) && date.before(endd)) {
                                sharedPreferences = v.getContext().getSharedPreferences(LOGIN, 0);
                                Map<String, String> param1 = new HashMap<>();
                                param1.put("id_murid", sharedPreferences.getString(ID, null));
                                param1.put(ID_SOAL_UJIAN, idSoalUjian.getText().toString());
                                param1.put(ID_JADWAL_UJIAN, id.getText().toString());

                                Call<SudahUjianResponse> call1 = apiInterface.getSudahUjian(param1);
                                call1.enqueue(new Callback<SudahUjianResponse>() {
                                    @Override
                                    public void onResponse(Call<SudahUjianResponse> call, Response<SudahUjianResponse> response) {
                                        int banyak = Integer.valueOf(response.body().getBanyak());
                                        if (banyak == 0) {
                                            Intent intent = new Intent(v.getContext(), IkutUjianActivity.class);
                                            intent.putExtra(ID_SOAL_UJIAN, idSoalUjian.getText().toString());
                                            intent.putExtra(ID_JADWAL_UJIAN, id.getText().toString());
                                            v.getContext().startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(v.getContext(), "Sudah ujian", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SudahUjianResponse> call, Throwable t) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(v.getContext(), "Waktu sudah lewat", Toast.LENGTH_SHORT).show();
                            }
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

