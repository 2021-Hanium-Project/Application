package com.cookandroid.hanium;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class bulletinFragment extends Fragment {
    Button free_btn, complain_btn, market_btn, fix_btn;
    MainActivity mainActivity;
    String id;
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
        View v = inflater.inflate(R.layout.fragment_bulletin, container, false);
        free_btn = v.findViewById(R.id.free_btn);
        complain_btn = v.findViewById(R.id.complain_btn);
        market_btn = v.findViewById(R.id.market_btn);
        fix_btn = v.findViewById(R.id.fix_btn);

        free_btn.setOnClickListener(onClickListener);
        fix_btn.setOnClickListener(onClickListener);
        Bundle bundle = getArguments();
        id =bundle.getString("id");


        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.free_btn:
                    mainActivity.onClickFreeBulletinBtn();
                    break;
                case R.id.fix_btn:
                    Intent intent = new Intent(getContext(), fixPopUp.class);
                    intent.putExtra("id",id);
                    startActivity(intent);

            }
        }

    };

}