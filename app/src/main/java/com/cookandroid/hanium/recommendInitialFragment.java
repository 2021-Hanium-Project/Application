package com.cookandroid.hanium;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recommendInitialFragment extends Fragment {
    MainActivity mainActivity;
    ServiceApi service;
    RecyclerView recyclerView;
    CheckBox filtering_dom1,filtering_dom2, filtering_dom3,
            filtering_smoker, filtering_nonsmoker,
            filtering_yesSleepHabit,filtering_noSleepHabit,
            filtering_foodAllow,filtering_foodPartiallyAllow,filtering_noFood,
            filtering_slpBefore9, filtering_slp9to12,filtering_slpAfter12,
            filtering_clean0to2, filtering_clean3to6,filtering_cleanEveryday ,
            filtering_shower0to2,filtering_shower3to6,filtering_showerEveryday;
    Button initial;
    String id;
    boolean inputCheck;
    LinearLayout filteringLayout;
    ConstraintLayout inputAlarmLayout;
    Button filteringSettingBtn,filteringBtn;
    ArrayList<CheckBox> checkBoxes;
    ArrayList<String> filteringArray = new ArrayList<>();
    ArrayList<String> checkboxName = new ArrayList<>();
    ArrayList<RecommendData> arrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;

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

        View v = inflater.inflate(R.layout.fragment_recommend_initial, container, false);
        sharedPreferences = getContext().getSharedPreferences("preferences",MODE_PRIVATE);
        inputCheck = sharedPreferences.getBoolean("inputCheck", false);
        Log.d("test", inputCheck+"");
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Button myBtn = v.findViewById(R.id.my_btn);
        Button filteringSettingBtn = v.findViewById(R.id.filtering_setting_btn);
        filtering_dom1 = v.findViewById(R.id.filtering_dom1);
        filtering_dom2 = v.findViewById(R.id.filtering_dom2);
        filtering_dom3 = v.findViewById(R.id.filtering_dom3);
        filtering_smoker = v.findViewById(R.id.filtering_smoker);
        filtering_nonsmoker = v.findViewById(R.id.filtering_nonsmoker);
        filtering_yesSleepHabit = v.findViewById(R.id.filtering_yesSleepHabit);
        filtering_noSleepHabit = v.findViewById(R.id.filtering_noSleepHabit);
        filtering_foodAllow = v.findViewById(R.id.filtering_foodAllow);
        filtering_foodPartiallyAllow = v.findViewById(R.id.filtering_foodPartiallyAllow);
        filtering_noFood = v.findViewById(R.id.filtering_noFood);
        filtering_slpBefore9 = v.findViewById(R.id.filtering_slpBefore9);
        filtering_slp9to12 = v.findViewById(R.id.filtering_slp9to12);
        filtering_slpAfter12 = v.findViewById(R.id.filtering_slpAfter12);
        filtering_clean0to2 = v.findViewById(R.id.filtering_clean0to2);
        filtering_clean3to6 = v.findViewById(R.id.filtering_clean3to6);
        filtering_cleanEveryday = v.findViewById(R.id.filtering_cleanEveryday);
        filtering_shower0to2 = v.findViewById(R.id.filtering_shower0to2);
        filtering_shower3to6 = v.findViewById(R.id.filtering_shower3to6);
        filtering_showerEveryday = v.findViewById(R.id.filtering_showerEveryday);
        inputAlarmLayout = v.findViewById(R.id.input_alarm_layout);

        initial = v.findViewById(R.id.initial);

        checkBoxes = new ArrayList<>(Arrays.asList(filtering_dom1, filtering_dom2, filtering_dom3,
                filtering_smoker, filtering_nonsmoker,
                filtering_yesSleepHabit, filtering_noSleepHabit,
                filtering_foodAllow, filtering_foodPartiallyAllow, filtering_noFood,
                filtering_slpBefore9, filtering_slp9to12, filtering_slpAfter12,
                filtering_clean0to2, filtering_clean3to6, filtering_cleanEveryday,
                filtering_shower0to2, filtering_shower3to6, filtering_showerEveryday));

        checkboxName = new ArrayList<>(Arrays.asList("1기숙사","2기숙사","3기숙사","흡연자","비흡연자","괜찮음","절대안됨",
                                                        "가능","일부가능","불가능","9시 이전","9~12시","12시 이후",
                                                        "c:주 0~2회","c:주 3~6회","c:매일",
                                                        "s:주 0~2회","s:주 3~6회","s:매일"));




        filteringLayout = v.findViewById(R.id.filtering_layout);
        filteringSettingBtn = v.findViewById(R.id.filtering_setting_btn);
        filteringBtn = v.findViewById(R.id.filtering_btn);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        Bundle bundle = getArguments();
        id = bundle.getString("id");

        if(inputCheck == true){
            myBtn.setEnabled(true);
            inputAlarmLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            filteringSettingBtn.setEnabled(true);
        }



        initial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    for(int i=0 ; i< checkBoxes.size(); i++){
                        if(checkBoxes.get(i).isChecked()){
                            checkBoxes.get(i).setChecked(false);
                        }
                    }
                    getAllList(id);

            }
        });

        filteringSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filteringLayout.getVisibility() == View.VISIBLE) {
                    filteringLayout.setVisibility(View.GONE);
                }
                else{
                    filteringLayout.setVisibility(View.VISIBLE);
                }

            }
        });

//        filtering_smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (filtering_smoke.isChecked()) {
//                    smoke = "흡연자";
//                    getFilteringList(id,smoke);
//                }
//                else{
//                    getAllList(id);
//
//                }
//            }
//        });


        getAllList(id);

        filteringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteringArray.clear();
                for(int i=0 ; i< checkBoxes.size(); i++){
                    if(checkBoxes.get(i).isChecked()){
                        filteringArray.add(checkboxName.get(i));
                    }
                }
                if(filteringArray.size()>0) {
                    getFilteringList(id, filteringArray);
                }
                else {
                    getAllList(id);
                }

            }
        });

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inputCheck",inputCheck);
                mainActivity.setIntent(intent);
                mainActivity.onClickMyBtn();
            }
        });



        return v;
    }
    public void getAllList(String id){
        service.getRecommendList(id).enqueue(new Callback<RecommendResponse>() {
            @Override
            public void onResponse(Call<RecommendResponse> call, Response<RecommendResponse> response) {
                if (response.isSuccessful()) {
                    RecommendResponse result = response.body();

                    arrayList.clear();
                    for (int i = 0; i < result.getData().size(); i++) {
                        arrayList.add(result.getData().get(i));

                    }


                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<RecommendResponse> call, Throwable t) {
                Log.d("test", "실패");
            }
        });

    }

    public void getFilteringList(String id, ArrayList<String> filteringArray){
        service.getRecommendFilteringResultList(id,filteringArray).enqueue(new Callback<RecommendResponse>() {
            @Override
            public void onResponse(Call<RecommendResponse> call, Response<RecommendResponse> response) {
                if(response.isSuccessful()){
                    RecommendResponse result = response.body();
                    arrayList.clear();
                    for(int i = 0 ; i<result.getData().size(); i++) {
                        arrayList.add(result.getData().get(i));

                    }


                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RecommendResponse> call, Throwable t) {

            }
        });
}

}
