package com.cookandroid.hanium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loginActivity extends AppCompatActivity {
    private EditText loginNumber,loginPw;
    private Button loginBtn, joinBtn;
    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginNumber = findViewById(R.id.login_number);
        loginPw = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_button);
        joinBtn = findViewById(R.id.join_button);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), joinActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        loginNumber.setError(null);
        loginPw.setError(null);

        String id = loginNumber.getText().toString();
        String pw = loginPw.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (id.isEmpty()) {
            loginNumber.setError("사번을 입력해주세요.");
            focusView = loginNumber;
            cancel = true;
        }

        if (pw.isEmpty()) {
            loginPw.setError("비밀번호를 입력해주세요.");
            focusView = loginPw;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(id, pw));
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();

                Toast.makeText(loginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(loginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());

            }
        });
    }

}