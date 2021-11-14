package com.example.mycommunity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list1);
        setContentView(R.layout.activity_list2);
        setContentView(R.layout.activity_list3);
        setContentView(R.layout.activity_list4);


        setContentView(R.layout.activity_register1);
        setContentView(R.layout.activity_register2);
        setContentView(R.layout.activity_register3);
        setContentView(R.layout.activity_register4);

    }
}