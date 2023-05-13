package com.example.cis_470_term_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DreamDetailActivity extends AppCompatActivity {

    private TextView dreamTitle;
    private TextView dreamDate;
    private TextView dreamDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_detail);

        dreamTitle = findViewById(R.id.dream_title);
        dreamDate = findViewById(R.id.dream_date);
        dreamDescription = findViewById(R.id.dream_description);

        String title = getIntent().getStringExtra("DREAM_TITLE");
        String date = getIntent().getStringExtra("DREAM_DATE");
        String description = getIntent().getStringExtra("DREAM_DESCRIPTION");

        dreamTitle.setText(title);
        dreamDate.setText(date);
        dreamDescription.setText(description);
    }
}