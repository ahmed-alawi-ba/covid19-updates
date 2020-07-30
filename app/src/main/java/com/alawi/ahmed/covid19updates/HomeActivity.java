package com.alawi.ahmed.covid19updates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    Button symBtn;
    Button treatmentBtn;
    Button updatesBtn;
    Button newsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        symBtn = (Button)findViewById(R.id.symBtn);
        treatmentBtn = (Button)findViewById(R.id.treatmentBtn);
        updatesBtn= (Button)findViewById(R.id.updatesBtn);
        newsBtn= (Button)findViewById(R.id.newsBtn);


        symBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), symActivity.class));
            }
        });

        treatmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), treatmentActivity.class));
            }
        });

        updatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LiveActivity.class));
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });


    }
}
