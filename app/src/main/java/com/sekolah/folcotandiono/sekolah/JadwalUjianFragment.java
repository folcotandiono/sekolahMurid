package com.sekolah.folcotandiono.sekolah;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sekolah.folcotandiono.sekolah.adapter.JadwalUjianAdapter;
import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjian;
import com.sekolah.folcotandiono.sekolah.model.JadwalUjianResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sekolah.folcotandiono.sekolah.LoginActivity.ID;
import static com.sekolah.folcotandiono.sekolah.LoginActivity.LOGIN;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JadwalUjianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JadwalUjianFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JadwalUjianFragment() {
        // Required empty public constructor
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;

    private RecyclerView recyclerView;

    public static String ID_SOAL_UJIAN = "id_soal_ujian";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JadwalUjianFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JadwalUjianFragment newInstance(String param1, String param2) {
        JadwalUjianFragment fragment = new JadwalUjianFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal_ujian, container, false);

        initView(view);
        initObject();
        initListener();

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void initObject() {
        getActivity().setTitle("Jadwal Ujian");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initListener() {
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = getActivity().getSharedPreferences(LOGIN, 0);

        Map<String, String> param = new HashMap<>();
        param.put(ID, sharedPreferences.getString(ID, null));

        Call<JadwalUjianResponse> call = apiInterface.getDataJadwalUjian(param);
        call.enqueue(new Callback<JadwalUjianResponse>() {
            @Override
            public void onResponse(Call<JadwalUjianResponse> call, Response<JadwalUjianResponse> response) {
                List<JadwalUjian> listJadwalUjian = response.body().getListJadwalUjian();
                JadwalUjianAdapter jadwalUjianAdapter = new JadwalUjianAdapter(listJadwalUjian);
                recyclerView.setAdapter(jadwalUjianAdapter);
            }

            @Override
            public void onFailure(Call<JadwalUjianResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
