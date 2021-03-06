package com.cookandroid.hanium;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recommendInputFragment extends Fragment {
    private RadioGroup inputSex, inputDom, inputSmoke, inputSleepHabit, inputFood, inputSleepTime, inputNumberOfCleaning, inputNumberOfShower;
    private CheckBox snoring, teethGrinding, sleepTalking;
    private Button inputBtn, back_btn;
    private LinearLayout sleepHabitYesLayout;
    private EditText titleEditText, descEditText;
    private ServiceApi service;
    private String id, sex, dom, smoke, sleepHabit, food, sleepTime, numberOfCleaning, numberOfShower, title, desc;
    private List<String> arr = new ArrayList<>();
    private boolean inputCheck;
    MainActivity mainActivity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommend_input, container, false);

        Bundle bundle = getArguments();
        id = bundle.getString("id");
        inputCheck = bundle.getBoolean("inputCheck");
        sharedPreferences = getContext().getSharedPreferences("preferences",MODE_PRIVATE);

        inputSex = v.findViewById(R.id.sex);
        inputDom = v.findViewById(R.id.dom);
        inputSmoke = v.findViewById(R.id.smoke);

        inputSleepHabit = v.findViewById(R.id.inputSleepHabit);
        sleepHabitYesLayout = v.findViewById(R.id.sleepHabitYesLayout);
        snoring = v.findViewById(R.id.snoring);
        teethGrinding = v.findViewById(R.id.teethGrinding);
        sleepTalking = v.findViewById(R.id.sleepTalking);

        inputFood = v.findViewById(R.id.food);
        inputSleepTime = v.findViewById(R.id.sleepTime);
        inputNumberOfCleaning = v.findViewById(R.id.NumberOfCleaning);
        inputNumberOfShower = v.findViewById(R.id.NumberOfShower);

        titleEditText = v.findViewById(R.id.titleEditText);
        descEditText = v.findViewById(R.id.descEditText);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        inputBtn = v.findViewById(R.id.inputBtn);
        back_btn = v.findViewById(R.id.rr_back_btn);

        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptInfoSend();

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onClickBackBtn();
            }
        });

        inputSleepHabit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yesSleepHabit) {
                    sleepHabitYesLayout.setVisibility(View.VISIBLE);
                } else {
                    sleepHabitYesLayout.setVisibility(View.GONE);
                }
            }
        });


        snoring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (snoring.isChecked()) {
                    arr.add(snoring.getText().toString());
                } else {
                    if (arr.contains(snoring.getText()) == true)
                        arr.remove(arr.indexOf((snoring.getText())));
                }
            }
        });

        teethGrinding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (teethGrinding.isChecked()) {
                    arr.add(teethGrinding.getText().toString());
                } else {
                    if (arr.contains(teethGrinding.getText()) == true)
                        arr.remove(arr.indexOf((teethGrinding.getText())));
                }
            }
        });

        sleepTalking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sleepTalking.isChecked()) {
                    arr.add(sleepTalking.getText().toString());
                } else {
                    if (arr.contains(sleepTalking.getText()) == true)
                        arr.remove(arr.indexOf((sleepTalking.getText())));
                }
            }
        });
        return v;
    }

    private void attemptInfoSend() {
        boolean cancel = false;
        View focusView = null;

        //??????
        switch (inputSex.getCheckedRadioButtonId()) {
            case R.id.male:
                sex = "??????";
                break;
            case R.id.female:
                sex = "??????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }


        //?????????
        switch (inputDom.getCheckedRadioButtonId()) {
            case R.id.dom1:
                dom = "1?????????";
                break;
            case R.id.dom2:
                dom = "2?????????";
                break;
            case R.id.dom3:
                dom = "3?????????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //?????? ??????
        switch (inputSmoke.getCheckedRadioButtonId()) {
            case R.id.smoker:
                smoke = "?????????";
                break;
            case R.id.nonsmoker:
                smoke = "????????????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //?????????
        switch (inputSleepHabit.getCheckedRadioButtonId()) {
            case R.id.yesSleepHabit:{
                sleepHabit = arr.toString();
            }
            break;
            case R.id.noSleepHabit:
                sleepHabit = "??????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }


        //?????? ??????
        switch (inputFood.getCheckedRadioButtonId()) {
            case R.id.foodAllow:
                food = "??????";
                break;
            case R.id.foodPartiallyAllow:
                food = "?????? ??????";
                break;
            case R.id.noFood:
                food = "?????????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //?????? ?????? ??????
        switch (inputSleepTime.getCheckedRadioButtonId()) {
            case R.id.slpBefore9:
                sleepTime = "9??? ??????";
                break;
            case R.id.slp9to12:
                sleepTime = "9~12???";
                break;
            case R.id.slpAfter12:
                sleepTime = "12??? ??????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "?????? ?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //?????? ??????
        switch (inputNumberOfCleaning.getCheckedRadioButtonId()) {
            case R.id.clean0to2:
                numberOfCleaning = "0~2???";
                break;
            case R.id.clean3to6:
                numberOfCleaning = "3~6???";
                break;
            case R.id.cleanEveryday:
                numberOfCleaning = "??????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //?????? ??????
        switch (inputNumberOfShower.getCheckedRadioButtonId()) {
            case R.id.shower0to2:
                numberOfShower = "0~2???";
                break;
            case R.id.shower3to6:
                numberOfShower = "3~6???";
                break;
            case R.id.showerEveryday:
                numberOfShower = "??????";
                break;
            default:
                cancel=true;
                Toast.makeText(getContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        //??????
        title = titleEditText.getText().toString();
        desc = descEditText.getText().toString();
        if(title.isEmpty()){
            titleEditText.setError("????????? ??????????????????");
            focusView = titleEditText;
            cancel = true;
        }

        if(desc.isEmpty()){
            descEditText.setError("????????? ??????????????????");
            focusView = descEditText;
            cancel = true;
        }



        if (cancel) {
            focusView.requestFocus();
        } else {
            startRRInfoSend(new RecommendData(id, sex, dom, smoke, sleepHabit, food, sleepTime, numberOfCleaning, numberOfShower, title, desc));
        }

    }

    private void startRRInfoSend(RecommendData data) {
        service.userRRInfo(data).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().get("message"), Toast.LENGTH_SHORT).show();
                    inputCheck = Boolean.valueOf(response.body().get("inputCheck")) ;
                    editor = sharedPreferences.edit();
                    editor.putBoolean("inputCheck",true);
                    editor.commit();
                    mainActivity.onClickInputBtn();
                    Log.d("test", inputCheck+"");
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Toast.makeText(getContext(), "???????????? ?????? ?????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                Log.e("?????? ?????? ?????? ??????", t.getMessage());

            }
        });
    }
}

