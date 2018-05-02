package com.sekolah.folcotandiono.sekolah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sekolah.folcotandiono.sekolah.api.ApiClient;
import com.sekolah.folcotandiono.sekolah.api.ApiInterface;
import com.sekolah.folcotandiono.sekolah.model.Murid;
import com.sekolah.folcotandiono.sekolah.model.MuridLoginResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText loginId;
    private EditText loginPassword;
    private Button loginLogin;
    private TextView konfigurasiIp;
    private ApiInterface loginApiInterface;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static String LOGIN = "login";
    public static String ID = "id";
    public static String IP_ADDRESS = "ip_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initObject();
        initListener();

        if (sharedPreferences.getString(ID, null) != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        loginId = findViewById(R.id.login_id);
        loginPassword = findViewById(R.id.login_password);
        loginLogin = findViewById(R.id.login_login);
        konfigurasiIp = findViewById(R.id.konfigurasi_ip);
    }

    private void initObject() {
        loginApiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
        sharedPreferences = getApplicationContext().getSharedPreferences(LOGIN, 0);
        editor = sharedPreferences.edit();
    }

    private void initListener() {
        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = loginId.getText().toString();
                String password = loginPassword.getText().toString();

                if (id.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                Murid murid = new Murid();
                murid.setId(id);
                murid.setPassword(password);

                loginApiInterface = ApiClient.getClient(getApplicationContext()).create(ApiInterface.class);
                Call<MuridLoginResponse> call = loginApiInterface.muridLogin(murid);
                call.enqueue(new Callback<MuridLoginResponse>() {
                    @Override
                    public void onResponse(Call<MuridLoginResponse> call, Response<MuridLoginResponse> response) {
                        List<Murid> listMurid = response.body().getListMurid();
                        if (listMurid.size() == 0) {
                            Toast.makeText(LoginActivity.this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sharedPreferences = getSharedPreferences(LOGIN, 0);
                            editor = sharedPreferences.edit();
                            editor.putString(ID, id);
                            editor.commit();
                            String haha = sharedPreferences.getString(ID, null);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<MuridLoginResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        konfigurasiIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KonfigurasiIpActivity.class);
                startActivity(intent);
            }
        });
    }
}
