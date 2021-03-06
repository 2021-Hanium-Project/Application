package com.cookandroid.hanium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fixPopUp extends Activity {
    RadioGroup organizationRG, contentRG;
    RadioButton dormA, dormB, vacuum, washingMachine, microwave;
    String id, organization, content, date;
    Button fixRegisterBtn;
    ServiceApi service;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd a HH:mm");


    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fix_popup);

            service = RetrofitClient.getClient().create(ServiceApi.class);

            organizationRG = findViewById(R.id.organization);
            contentRG = findViewById(R.id.content);

            dormA = findViewById(R.id.dormA);
            dormB = findViewById(R.id.dormB);
            vacuum = findViewById(R.id.vacuum);
            washingMachine = findViewById(R.id.washing_machine);
            microwave = findViewById(R.id.microwave);

            fixRegisterBtn = findViewById(R.id.fix_register_btn);

            Intent intent = getIntent();
            id = intent.getStringExtra("id");

            mNow = System.currentTimeMillis();
            mDate = new Date(mNow);
            mFormat.format(mDate);
            date = mFormat.format(mDate);

            fixRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptFixSend();

            }
        });




        }
        private void attemptFixSend(){
            boolean cancel = false;
            View focusView = null;

            //??????
            switch (organizationRG.getCheckedRadioButtonId()) {
                case R.id.dormA:
                    organization = "A???";
                    break;
                case R.id.dormB:
                    organization = "B???";
                    break;
                default:
                    focusView = organizationRG;
                    cancel=true;
                    Toast.makeText(this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }


            //??????
            switch (contentRG.getCheckedRadioButtonId()) {
                case R.id.vacuum:
                    content = "?????????";
                    break;
                case R.id.washing_machine:
                    content = "?????????";
                    break;
                case R.id.microwave:
                    content = "???????????????";
                    break;
                default:
                    focusView = contentRG;
                    cancel=true;
                    Toast.makeText(this, "?????? ?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                startFixSend(new FixData(id,organization,content,date));
            }

    }

    private void startFixSend(FixData data) {
            service.sendFix(data).enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),response.body().get("message"), Toast.LENGTH_SHORT).show();

                    }else{
                        Log.d("test", response.message()+"");
                    }
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "?????? ?????? ?????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    Log.e("?????? ?????? ?????? ?????? ??????", t.getMessage());

                }
            });

    }



}
