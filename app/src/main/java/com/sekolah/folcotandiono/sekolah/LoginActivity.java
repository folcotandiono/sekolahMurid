package com.sekolah.folcotandiono.sekolah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginLogin;
    private ApiInterface loginApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initObject();
        initListener();
    }

    private void initView() {
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        loginLogin = findViewById(R.id.loginLogin);
    }

    private void initObject() {
        loginApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void initListener() {
        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Murid murid = new Murid();
                murid.setId(username);
                murid.setPassword(password);

                Call<MuridLoginResponse> call = loginApiInterface.muridLogin(murid);
                call.enqueue(new Callback<MuridLoginResponse>() {
                    @Override
                    public void onResponse(Call<MuridLoginResponse> call, Response<MuridLoginResponse> response) {
                        List<Murid> listMurid = response.body().getListMurid();
                        if (listMurid.size() == 0) {
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<MuridLoginResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
