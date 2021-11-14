package com.cookandroid.hanium;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class joinActivity extends AppCompatActivity {
    private EditText joinID,joinPw,joinPwChk,joinEmail,joinNickname;
    private Button idchk, joinBtn;
    private ServiceApi service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);


        joinID = findViewById(R.id.id);
        joinPw = findViewById(R.id.pw);
        joinPwChk = findViewById(R.id.pwchk);
        joinEmail = findViewById(R.id.email);
        joinNickname = findViewById(R.id.nickname);
        idchk = findViewById(R.id.idchk);
        joinBtn = findViewById(R.id.joinBtn);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        idchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptIdChk();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });
    }

    private void attemptIdChk() {
        joinID.setError(null);
        String id = joinID.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //사번의 유효성 검사
        if (id.isEmpty()) {
            joinID.setError("사번을 입력해주세요.");
            focusView = joinID;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startIdChk(new IdChkData(id));

        }
    }

    private void startIdChk(IdChkData data) {
        service.userIdchk(data).enqueue(new Callback<IdChkResponse>() {
            @Override
            public void onResponse(Call<IdChkResponse> call, Response<IdChkResponse> response) {
                IdChkResponse result = response.body();
                View focusView = null;

                if(result.getCode() == 204){
                    joinID.setError("이미 사용중인 아이디입니다.");
                    focusView=joinID;
                    focusView.requestFocus();
                }else {
                    Toast.makeText(joinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IdChkResponse> call, Throwable t) {
                Toast.makeText(joinActivity.this, "중복확인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("중복확인 에러 발생", t.getMessage());

            }
        });
    }

    private void attemptJoin() {
        joinID.setError(null);
        joinPw.setError(null);
        joinPwChk.setError(null);
        joinEmail.setError(null);
        joinNickname.setError(null);

        String id = joinID.getText().toString();
        String pw = joinPw.getText().toString();
        String pwChk = joinPwChk.getText().toString();
        String email = joinEmail.getText().toString();
        String nickname = joinNickname.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //사번의 유효성 검사
        if (id.isEmpty()) {
            joinID.setError("사번을 입력해주세요.");
            focusView = joinID;
            cancel = true;
        }

        // 패스워드의 유효성 검사
        if (pw.isEmpty()) {
            joinPw.setError("비밀번호를 입력해주세요.");
            focusView = joinPw;
            cancel = true;
        }

        //패스워드 확인 유효성 검사
        if (pwChk.isEmpty()) {
            joinPwChk.setError("비밀번호 확인을 입력해주세요.");
            focusView = joinPwChk;
            cancel = true;
        }else if (pw.equals(pwChk) == false){
            joinPwChk.setError("비밀번호와 비밀번호 확인이 다릅니다.");
            focusView = joinPwChk;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            joinEmail.setError("이메일을 입력해주세요.");
            focusView = joinEmail;
            cancel = true;
        }else if (!isEmailValid(email)) {
            joinEmail.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = joinEmail;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (nickname.isEmpty()) {
            joinNickname.setError("닉네임을 입력해주세요.");
            focusView = joinNickname;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData(id,pw,pwChk,email,nickname));

        }
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(joinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(joinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());

            }
        });
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

}


