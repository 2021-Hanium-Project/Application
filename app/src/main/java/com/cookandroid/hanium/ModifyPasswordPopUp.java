package com.cookandroid.hanium;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPasswordPopUp extends Activity {
    EditText currentPasswordET, newPasswordET, checkNewPasswordET;
    Button modifyBtn;
    String id, currentPassword, newPassword, checkNewPassword;
    TextView tempPasswordTv;
    private ServiceApi service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_password_popup);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        service = RetrofitClient.getClient().create(ServiceApi.class);

        currentPasswordET = findViewById(R.id.currentPassword);
        newPasswordET = findViewById(R.id.newPassword);
        checkNewPasswordET = findViewById(R.id.checkNewPassword);
        modifyBtn = findViewById(R.id.modifyBtn);

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptModifyPassword();
            }
        });


    }


    private void attemptModifyPassword() {
        boolean cancel = false;
        View focusView = null;

        currentPassword = currentPasswordET.getText().toString();
        newPassword = newPasswordET.getText().toString();
        checkNewPassword = checkNewPasswordET.getText().toString();

        if (currentPassword.isEmpty()) {
            currentPasswordET.setError("?????? ??????????????? ??????????????????.");
            focusView = currentPasswordET;
            cancel = true;
        }

        if (newPassword.isEmpty()) {
            newPasswordET.setError("??? ??????????????? ??????????????????.");
            focusView = newPasswordET;
            cancel = true;
        }

        if (!newPassword.equals(checkNewPassword)) {
            newPasswordET.setError("??? ??????????????? ????????? ????????????.");
            focusView = newPasswordET;
            cancel = true;
        }


        if (checkNewPassword.isEmpty()) {
            checkNewPasswordET.setError("??? ???????????? ????????? ??????????????????.");
            focusView = checkNewPasswordET;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startModifyCurrentPassword(id, currentPassword, newPassword);
        }
    }


    private void startModifyCurrentPassword(String id, String currentPassword,String newPassword) {
        service.modifyPassword(id, currentPassword, newPassword).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                HashMap<String, String> result = response.body();
                int resultCode = Integer.parseInt(result.get("code"));

                if(resultCode ==204){
                    Toast.makeText(ModifyPasswordPopUp.this, result.get("message"), Toast.LENGTH_SHORT).show();
                }
                if(resultCode ==200){
                    Toast.makeText(ModifyPasswordPopUp.this, result.get("message"), Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(ModifyPasswordPopUp.this, "???????????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                Log.e("???????????? ?????? ?????? ??????", t.getMessage());

            }
        });
    }
}
