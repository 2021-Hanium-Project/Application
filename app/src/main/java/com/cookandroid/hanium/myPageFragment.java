package com.cookandroid.hanium;

import static com.cookandroid.hanium.RetrofitClient.getClient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myPageFragment extends Fragment {
    ServiceApi service;
    TextView nicknameTV, idTV, emailTV, modifyPasswordBtn, fixApplyListBtn;
    String id;
    ListView fixApplyList;
    ArrayList<String> fixArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        service = getClient().create(ServiceApi.class);
        nicknameTV = v.findViewById(R.id.myPage_nickname);
        idTV = v.findViewById(R.id.myPage_id);
        emailTV = v.findViewById(R.id.myPage_email);
        modifyPasswordBtn = v.findViewById(R.id.modifyPasswordBtn);
        fixApplyListBtn = v.findViewById(R.id.fix_apply_list_TV);
        fixApplyList = v.findViewById(R.id.fix_apply_list);

        Bundle bundle = getArguments();
        id =bundle.getString("id");

        modifyPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ModifyPasswordPopUp.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        service.getMyInformation(id).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                HashMap<String, String> result = response.body();

                idTV.setText(id);
                nicknameTV.setText(result.get("nickname"));
                emailTV.setText(result.get("email"));

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                Log.e("회원가입 에러 발생", t.getMessage());

            }
        });

        service.getFixList(id).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                HashMap<String, String> result = response.body();
                Log.d("test",result.get("data"));
                /*for(int i = 0 ; i<result.size(); i++) {
                    fixArraylist.add(result.get(i));
                    Log.d("test",result.get("data"));


                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, fixArraylist);
                fixApplyList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               */

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                Log.e("회원가입 에러 발생", t.getMessage());

            }
        });







        return v;
    }

}