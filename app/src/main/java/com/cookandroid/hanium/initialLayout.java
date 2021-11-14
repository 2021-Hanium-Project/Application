package com.cookandroid.hanium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class initialLayout extends AppCompatActivity {
    Button schoolbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);

        final String[] school = {"인천대학교","대학교1","대학교2"};
        Spinner spinner = findViewById(R.id.school_spinner);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,school);
        spinner.setAdapter(adapter);

        schoolbtn = findViewById(R.id.school_btn);
        schoolbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
